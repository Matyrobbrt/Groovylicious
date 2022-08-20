package ga.ozli.minecraftmods.groovylicious.transform.registroid

import groovy.transform.CompileStatic

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Documented
@CompileStatic
@Target([ElementType.FIELD, ElementType.TYPE])
@Retention(RetentionPolicy.SOURCE)
@interface RegistrationName {
    String value()

    boolean alwaysApply() default false
}