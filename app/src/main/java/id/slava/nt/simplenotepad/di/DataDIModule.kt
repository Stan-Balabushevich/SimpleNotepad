package id.slava.nt.simplenotepad.di

import androidx.room.Room
import id.slava.nt.simplenotepad.data.repository.NoteRepositoryImpl
import id.slava.nt.simplenotepad.data.source.NoteDatabase
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import org.koin.dsl.module

val dataModule = module {

  // more concise way
    single {
        Room.databaseBuilder(
            get(),
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    single {
        get<NoteDatabase>().noteDao
    }

    single<NoteRepository> {
        NoteRepositoryImpl(dao = get()) }


// more understandable way
//    fun provideDB( application: Application): NoteDatabase =
//            Room.databaseBuilder(
//                application,
//                NoteDatabase::class.java,
//                NoteDatabase.DATABASE_NAME
//            ).build()
//
//    fun provideDao(db: NoteDatabase): NoteDao =
//        db.noteDao
//
//    single { provideDB(androidApplication()) }
//    single { provideDao(get()) }
//    single<NoteRepository> {  NoteRepositoryImpl(dao = get()) }

}