/*
 * @author   Anoop Maddasseri <anoopmaddasseri@gmail.com>
 * @version  1
 * @since    16th Feb 2020
 *
 * P.S. Increment version when editing
 */
package com.forthcode.customerapp.di.modules

import com.forthcode.customerapp.di.ActivityScope
import com.forthcode.customerapp.home.di.HomeModule
import com.forthcode.customerapp.home.di.HomeRepoBindingModule
import com.forthcode.customerapp.home.presentation.HomeActivity
import com.forthcode.customerapp.login.di.LoginModule
import com.forthcode.customerapp.login.di.LoginRepoBindingModule
import com.forthcode.customerapp.login.presentation.LoginActivity
import com.forthcode.customerapp.signup.di.SignupModule
import com.forthcode.customerapp.signup.di.SignupRepoBindingModule
import com.forthcode.customerapp.signup.presentation.SignupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [LoginRepoBindingModule::class, SignupRepoBindingModule::class,HomeRepoBindingModule::class])
abstract class ActivityBuilderModule {

//    @ActivityScope
//    @ContributesAndroidInjector(modules = [BaseModule::class])
//    abstract fun provideBas(): Basea

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun provideLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SignupModule::class])
    abstract fun provideSignupActivity(): SignupActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun provideHomeActivity() : HomeActivity

}