# Jetpack Compose Example - MVI-Navigation-Permissions-Koin

This is just a sample project to get familiar with MVI, and how to use combine it with Navigation and Permissions Handling when using Jetpack Compose, with Koin as Dependency Injection

In this sample, I have just laid out the basic architecture needed to develop applications. I haven't added Repository and Data layer as of now. Even with that, this is a complete flow for MVI architecutre as you can and will still use DATA layer of MVVM, as the major focus of MVI is to reduce UI state and events in recomposition. 


## Contacts Application

In this example, I have handled permissions of Reading Contacts and then displaying in our app, where State flows from View Model to Composables and Events flow upwards to view model. 
You can combine multiple permissions together or one-by-one throughout your app and use as per specified in the sample. 

I have laid out the navigation component as well, just did not implement yet. You can use it, just need to add screen destination in it. Will complete it (navigation+data kayer) as soon as get the time to do it.

![Screenshot_20221206_154322](https://user-images.githubusercontent.com/28254327/205894459-1b0bd455-1d75-45a2-b294-8694ad61fd64.png)

![Screenshot_20221206_154404](https://user-images.githubusercontent.com/28254327/205894483-7a754c97-0691-4982-b5b9-f6c0f4c90b08.png)

![Screenshot_20221206_154333](https://user-images.githubusercontent.com/28254327/205894505-0865f388-2ab2-4ef2-acb6-236e3d5cadc3.png)

![contactscreen](https://user-images.githubusercontent.com/28254327/205894549-f89f25a6-535f-4acb-82e3-cf9c5caa8d66.PNG)

