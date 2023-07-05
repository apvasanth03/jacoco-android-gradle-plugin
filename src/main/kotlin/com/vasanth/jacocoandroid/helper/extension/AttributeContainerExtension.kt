package com.vasanth.jacocoandroid.helper.extension

import com.android.build.api.attributes.BuildTypeAttr
import com.android.build.api.attributes.ProductFlavorAttr
import com.android.build.api.variant.Variant
import org.gradle.api.Project
import org.gradle.api.attributes.AttributeContainer
import org.gradle.api.attributes.Usage
import org.gradle.kotlin.dsl.named

/**
 * [AttributeContainer] Extension - To configure attributes for the Configuration.
 */
fun AttributeContainer.configureConfigurationAttributes(
    project: Project,
    variant: Variant,
    usageAttName: String
) {
    with(project) {
        attribute(
            Usage.USAGE_ATTRIBUTE,
            objects.named(usageAttName)
        )

        variant.buildType?.let { buildType ->
            attribute(
                BuildTypeAttr.ATTRIBUTE,
                objects.named(buildType)
            )
        }

        variant.productFlavors.forEach { productFlavor ->
            val dimension = productFlavor.first
            val flavor = productFlavor.second
            attribute(
                ProductFlavorAttr.of(dimension),
                objects.named(flavor)
            )
        }
    }
}