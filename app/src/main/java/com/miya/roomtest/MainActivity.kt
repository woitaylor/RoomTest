package com.miya.roomtest

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.withTransaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.google.gson.Gson
import com.miya.roomtest.data.*
import com.miya.roomtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject


/**
 * 读取assets本地json
 * @param fileName
 * @param context
 * @return
 */
fun getJson(fileName: String?, context: Context): String {
    //将json数据变成字符串
    val stringBuilder = StringBuilder()
    try {
        //获取assets资源管理器
        val assetManager: AssetManager = context.assets
        //通过管理器打开文件并读取
        val bf = BufferedReader(
            InputStreamReader(
                assetManager.open(fileName!!)
            )
        )
        var line: String?
        while (bf.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return stringBuilder.toString()
}


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var programmeDao: ProgrammeDao

    @Inject
    lateinit var adDao: AdDao

    @Inject
    lateinit var adSourceDao: AdSourceDao

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    clickProxy = ClickProxy()
                }
    }

    inner class ClickProxy {
        fun insert(v: View) {
            val programmeListBean = gson.fromJson(
                getJson("ProgrammeList.json", application),
                ProgrammeListBean::class.java
            )
            val programmeList = programmeListBean.programmeList
            for (programme in programmeList) {
                GlobalScope.launch {
                    appDatabase.withTransaction {
                        val adList = programme.adList
                        val programmeId = programme.programmeId
                        programmeDao.insertProgramme(covertProgrammeToEntity(programme))
                        val adEntities = adList.map {
                            covertAdToEntity(it, programmeId)
                        }.toList()
                        adDao.insertAllAd(adEntities)
                        for (ad in adList) {
                            val adId = ad.adId
                            val adSourceEntities = ad.adSourceList.map {
                                covertAdSourceToEntity(it, adId)
                            }.toList()
                            adSourceDao.insertAllAdSource(adSourceEntities)
                        }
                    }
                }
            }
        }

        fun query(v: View) {
            GlobalScope.launch {
//                adDao.getAdAnAdSources().collectLatest {
//                    Log.i("liang", gson.toJson(it))
//                }
                programmeDao.getProgrammeAndAd().collectLatest {
                    for (programmeAndAd in it) {
                        Log.i("liang", gson.toJson(programmeAndAd))
                        Log.i("liang", "adEntities size: ${programmeAndAd.adEntities.size}")
//                        val adIds = programmeAndAd.adEntities.map {ad->
//                            Log.i("liang", "ad id: ${ad.adId}")
//                            ad.adId
//                        }.toList()
//                        adDao.getAdAnAdSourcesById(adIds).collect { v->
//                            for(adAndAdSource in v) {
//                                Log.i("liang", gson.toJson(adAndAdSource))
//                            }
//                        }
//                        for (ad in programmeAndAd.adEntities) {
//                            Log.i("liang", "ad id: ${ad.adId}")
//                            adDao.getAdAnAdSourcesById(ad.adId).apply {
//                                Log.i("liang", "Flow obj: ${this.toString()}")
//                            }.collect { v ->
//                                Log.i("liang", gson.toJson(v))
//                            }
//                        }
                        (programmeAndAd.adEntities.indices).asFlow()
                            .transform { index ->
                                val adId = programmeAndAd.adEntities[index].adId
                                emit(adDao.getAdAnAdSourcesById(adId).first())
                            }
                            .collect { v ->
                                Log.i("liang", gson.toJson(v))
                            }
                    }
                }
            }
        }

        fun delete(v: View) {
            val supportSQLiteQuery =  SimpleSQLiteQuery("DELETE FROM programme WHERE programmeId = ?",  arrayOf(23))
            GlobalScope.launch {
                programmeDao.deleteProgrammeById(supportSQLiteQuery)
            }
        }
    }
}