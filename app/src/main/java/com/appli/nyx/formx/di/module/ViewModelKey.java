package com.appli.nyx.formx.di.module;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

/**
 * ViewModel Key which serves as the unique key for keeping the viewmodel instances in the factory
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {

	@SuppressWarnings("unused") Class<? extends ViewModel> value();
}