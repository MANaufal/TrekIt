package org.d3if3112.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class WorkerBackground(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    companion object {
        const val WORK_NAME = "updater"
    }
    override suspend fun doWork(): Result {
        Log.d("Worker", "Working in background")
        return Result.success()
    }
}