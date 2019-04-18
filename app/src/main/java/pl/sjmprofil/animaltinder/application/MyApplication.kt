package pl.sjmprofil.animaltinder.application

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import pl.sjmprofil.animaltinder.utilities.Validator

class MyApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {


        bind<Validator>() with singleton {
            Validator()
        }


//       bind<OkHttpClient>() with singleton {
//            OkHttpClient.Builder().build()
//        }
//
//        bind<Retrofit>() with singleton {
//            Retrofit.Builder()
//                .client(instance())
//                .baseUrl("http://api.icndb.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .build()
//        }
//
//        bind<Database>() with singleton {
//            Room.databaseBuilder(
//                applicationContext,
//                Database::class.java, "jokes.db"
//            )
//                .fallbackToDestructiveMigration()
//                .build()
//        }
//
//        bind<DatabaseRepository>() with singleton {
//            DatabaseRepository(instance())
//        }
//
//        bind<ApiRepository>() with singleton {
//            ApiRepository(instance(), applicationContext)
//        }
//
//        bind<JokesRecyclerAdapter>() with singleton {
//            JokesRecyclerAdapter()
//        }
//
//        bind<DatabaseJokesRecyclerAdapter>() with singleton {
//            DatabaseJokesRecyclerAdapter(RatingDescendingComparator())
//        }
//
//        bind() from singleton { instance<Retrofit>().create(JokeService::class.java) }
//
//        bind<RemoteFragmentViewModelFactory>() with singleton {
//            RemoteFragmentViewModelFactory(
//                instance(),
//                instance()
//            )
//        }
//
//        bind<LocalFragmentViewModelFactory>() with singleton {
//            LocalFragmentViewModelFactory(
//                instance()
//            )
//        }
//    }
    }
}