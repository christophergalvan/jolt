package com.jolt.core.models;

import com.adobe.cq.wcm.core.components.models.Teaser;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import com.adobe.cq.wcm.core.components.commons.link.Link;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, adapters = Teaser.class, resourceType = "jolt/components/contentBlock")
public class ContentBlockModel implements Teaser {

    @Self
    @Via(type = ResourceSuperType.class)
    private Teaser teaser;

    @ValueMapValue
    private String buttonText;

    @ValueMapValue
    private String buttonLink;

    public String getButtonText() {
        return buttonText;
    }

    public String getButtonLink() {
        return buttonLink;
    }

    public String getTitle() {
        return teaser.getTitle();
    }

    public String getPretitle() {
        return teaser.getPretitle();
    }

    public String getDescription() {
        return teaser.getDescription();
    }

    public String getLinkURL() {
        return teaser.getLink().getURL();
    }
}
