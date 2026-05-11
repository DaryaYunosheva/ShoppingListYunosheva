package com.example.shoppinglistyunosheva.di

import android.app.Application
import androidx.room.Room
import com.example.shoppinglistyunosheva.data.AddItemRepoImpl
import com.example.shoppinglistyunosheva.data.AddItemRepository
import com.example.shoppinglistyunosheva.data.MainDb
import com.example.shoppinglistyunosheva.data.NoteRepoImpl
import com.example.shoppinglistyunosheva.data.NoteRepository
import com.example.shoppinglistyunosheva.data.ShoppingListRepoImpl
import com.example.shoppinglistyunosheva.data.ShoppingListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//бд и репозиторий доступны во всех экранах, пока живет приложение
object AppModule {
    //инициализируем бд
    @Provides
    @Singleton //как только будет создан модуль, создатся только одна инстанция, каждый раз новые не создаются
    fun provideMainDb(app: Application): MainDb{
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "shop_list_db"
                ).build()
    }

    @Provides
    @Singleton
    fun provideShopRepo(db: MainDb): ShoppingListRepository{
        return ShoppingListRepoImpl(db.shoppingListDao)
    }

    @Provides
    @Singleton
    fun provideAddItemRepo(db: MainDb): AddItemRepository{
        return AddItemRepoImpl(db.addItemDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepo(db: MainDb): NoteRepository{
        return NoteRepoImpl(db.noteDao)
    }
}