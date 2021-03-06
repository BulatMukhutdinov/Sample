package ru.bulat.mukhutdinov.sample.infrastructure.util

import io.reactivex.Completable
import ru.bulat.mukhutdinov.sample.user.db.UserDao
import ru.bulat.mukhutdinov.sample.user.db.UserEntity
import java.util.UUID

class DummyDataProvider(private val userDao: UserDao) {

    fun generateUsersDummyData(): Completable =
        Completable
            .fromCallable {
                userDao.insert(UserEntity(
                    id = 1,
                    name = "Jalal Hokolesqua",
                    icon = "https://api.adorable.io/avatars/240/Jalal_Hokolesqua.png",
                    iconThumbnail = "https://api.adorable.io/avatars/64/Jalal_Hokolesqua.png",
                    description = "Gender:\tMasculine\n" +
                        "Type:\tAdult\n" +
                        "Nationality:\tIranian\n" +
                        "Location:\tTehran, Iran\n" +
                        "Language:\tPersian"))

                userDao.insert(UserEntity(
                    id = 2,
                    name = "Troels Maksym",
                    icon = "https://api.adorable.io/avatars/240/Troels_Maksym.png",
                    iconThumbnail = "https://api.adorable.io/avatars/64/Troels_Maksym.png",
                    description = "Gender:\tMasculine\n" +
                        "Type:\tElderly Adult\n" +
                        "Nationality:\tDanish\n" +
                        "Location:\tDenmark\n" +
                        "Language:\tDanish"))

                userDao.insert(UserEntity(
                    id = 3,
                    name = "Zdenka Gavrila",
                    icon = "https://api.adorable.io/avatars/240/Zdenka_Gavrila.png",
                    iconThumbnail = "https://api.adorable.io/avatars/64/Zdenka_Gavrila.png",
                    description = "Gender:\tFeminine\n" +
                        "Type:\tAdult\n" +
                        "Nationality:\tSlovak\n" +
                        "Location:\tSlovakia\n" +
                        "Language:\tSlovak"))

                userDao.insert(UserEntity(
                    id = 4,
                    name = "Asen Kallias",
                    icon = "https://api.adorable.io/avatars/240/Asen_Kallias.png",
                    iconThumbnail = "https://api.adorable.io/avatars/64/Asen_Kallias.png",
                    description = "Gender:\tMasculine\n" +
                        "Type:\tChild\n" +
                        "Nationality:\tBulgarian\n" +
                        "Location:\tBulgaria\n" +
                        "Language:\tBulgarian"))

                userDao.insert(UserEntity(
                    id = 5,
                    name = "Rufinus Calla",
                    icon = "https://api.adorable.io/avatars/240/Rufinus_Calla.png",
                    iconThumbnail = "https://api.adorable.io/avatars/64/Rufinus_Calla.png",
                    description = "Gender:\tMasculine\n" +
                        "Type:\tYoung Adult\n" +
                        "Nationality:\tItalian\n" +
                        "Location:\tSicily, Italy\n" +
                        "Language:\tItalian"))

            }
}