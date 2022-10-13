package com.jolt.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {FooterModel.class},
        resourceType = {FooterModel.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FooterModel {
    protected static final String RESOURCE_TYPE = "jolt/components/footer";

    @ValueMapValue
    private String text;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean textIsRich;

    public String getText() {
        return text;
    }

    public boolean isRichText() {
        return textIsRich;
    }
}
