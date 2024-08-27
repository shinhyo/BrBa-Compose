package io.github.shinhyo.brba.buildlogic

import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure

internal fun Project.configureKoverAndroid() {
    pluginManager.apply("org.jetbrains.kotlinx.kover")

    extensions.configure<KoverProjectExtension> {

//        useJacoco()

        reports {
            filters {
                includes {
                    classes("*ViewModel*")
                }
                excludes {
                    classes("*_Factory*", "*_HiltModules*")
                }
            }

            variant("debug") {
                html {
                    onCheck = true
                }

                xml {
                    onCheck = true
                }
            }

        }

    }

}


