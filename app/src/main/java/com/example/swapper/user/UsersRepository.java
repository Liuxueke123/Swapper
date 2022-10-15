package com.example.swapper.user;

//public class UsersRepository {
//    private static volatile UsersRepository instance;
//
//    private UsersDatabase usersDatabase;
//    private UsersDao usersDao;
//
//
//    public UsersRepository(Context context){
//        usersDatabase=Room.databaseBuilder(context,UsersDatabase.class,"swapper").allowMainThreadQueries().build();
//        usersDao=usersDatabase.getUsersDao();
//    }
//
//    public static UsersRepository getInstance(Context context){
//        if (instance==null){
//            synchronized (UsersRepository.class){
//                if (instance==null){
//                    instance=new UsersRepository(context);
//                }
//            }
//        }
//        return instance;
//    }
//
//    public UsersDao getUsersDao(){
//        return usersDao;
//    }
//
//
//}
