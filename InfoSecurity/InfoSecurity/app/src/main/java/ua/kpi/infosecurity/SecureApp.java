package ua.kpi.infosecurity;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ua.kpi.infosecurity.model.FileObj;
import ua.kpi.infosecurity.model.Permission;
import ua.kpi.infosecurity.model.User;
import ua.kpi.infosecurity.util.Prefs;

/**
 * Created on 3/28/16.
 */
public class SecureApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());

        initDataBase();
    }

    private void initDataBase() {
        Prefs prefs = Prefs.getInstance(this);
        if (!prefs.isInitialized()) {

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();


            User admin = realm.createObject(User.class);
            admin.setName("admin");
            admin.setEmail("admin@kpi.ua");
            admin.setPassword("admin");
            admin.setSecret(4);
            admin.setAllowedAuthFailures(10);
            admin.setUuid(UUID.randomUUID().toString());

            User user1 = realm.createObject(User.class);
            user1.setName("user1");
            user1.setEmail("user1@kpi.ua");
            user1.setPassword("qwerty");
            user1.setSecret(10);
            user1.setAllowedAuthFailures(1);
            user1.setUuid(UUID.randomUUID().toString());

            User user2 = realm.createObject(User.class);
            user2.setName("user2");
            user2.setEmail("user2@kpi.ua");
            user2.setPassword("qwerty");
            user2.setSecret(10);
            user2.setAllowedAuthFailures(10);
            user2.setUuid(UUID.randomUUID().toString());


            FileObj home = realm.createObject(FileObj.class);
            home.setUuid(UUID.randomUUID().toString());
            home.setName("home/");
            home.setType(FileObj.TYPE_FOLDER);


            FileObj docs = realm.createObject(FileObj.class);
            docs.setUuid(UUID.randomUUID().toString());
            docs.setName("Documents/");
            docs.setType(FileObj.TYPE_FOLDER);

            FileObj pics = realm.createObject(FileObj.class);
            pics.setUuid(UUID.randomUUID().toString());
            pics.setName("Pictures/");
            pics.setType(FileObj.TYPE_FOLDER);


            FileObj down = realm.createObject(FileObj.class);
            down.setUuid(UUID.randomUUID().toString());
            down.setName("Downloads/");
            down.setType(FileObj.TYPE_FOLDER);

            FileObj cat1 = realm.createObject(FileObj.class);
            cat1.setUuid(UUID.randomUUID().toString());
            cat1.setName("cat Boris.png");
            cat1.setType(FileObj.TYPE_FILE);

            FileObj cat2 = realm.createObject(FileObj.class);
            cat2.setUuid(UUID.randomUUID().toString());
            cat2.setName("cat Tom.png");
            cat2.setType(FileObj.TYPE_FILE);

            FileObj cat3 = realm.createObject(FileObj.class);
            cat3.setUuid(UUID.randomUUID().toString());
            cat3.setName("cat Fedor.png");
            cat3.setType(FileObj.TYPE_FILE);

            FileObj cat4 = realm.createObject(FileObj.class);
            cat4.setUuid(UUID.randomUUID().toString());
            cat4.setName("cat Igor.png");
            cat4.setType(FileObj.TYPE_FILE);

            FileObj cat5 = realm.createObject(FileObj.class);
            cat5.setUuid(UUID.randomUUID().toString());
            cat5.setName("cat Vova.png");
            cat5.setType(FileObj.TYPE_FILE);


            List<Permission> permissions = new ArrayList<>();
            permissions.add(new Permission(admin.getUuid(), home.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(admin.getUuid(), docs.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(admin.getUuid(), pics.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(admin.getUuid(), down.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(admin.getUuid(), cat1.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(admin.getUuid(), cat2.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(admin.getUuid(), cat3.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(admin.getUuid(), cat4.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(admin.getUuid(), cat5.getUuid(), Permission.READ_WRITE));

            permissions.add(new Permission(user1.getUuid(), down.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user1.getUuid(), cat1.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user1.getUuid(), cat2.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user1.getUuid(), cat3.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user1.getUuid(), cat4.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user1.getUuid(), cat5.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user1.getUuid(), pics.getUuid(), Permission.READ));

            permissions.add(new Permission(user2.getUuid(), down.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user2.getUuid(), cat1.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user2.getUuid(), cat2.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user2.getUuid(), cat3.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user2.getUuid(), cat4.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user2.getUuid(), cat5.getUuid(), Permission.READ_WRITE));
            permissions.add(new Permission(user2.getUuid(), pics.getUuid(), Permission.READ));

            for( Permission p : permissions) {
                realm.copyToRealm(p);
            }

            realm.commitTransaction();

            prefs.setIsInitialized();
        }
    }

}
