package id.slava.nt.simplenotepad

import android.app.Application
import id.slava.nt.simplenotepad.di.appModule
import id.slava.nt.simplenotepad.di.dataModule
import id.slava.nt.simplenotepad.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NoteApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@NoteApplication)
            modules(
                dataModule,
                domainModule,
                appModule
            )

        }

    }
}