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
import com.adobe.cq.wcm.core.components.models.Image;
import org.apache.sling.models.factory.ModelFactory;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import java.util.Optional;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, adapters = Teaser.class, resourceType = "jolt/components/contentBlock")
public class ContentBlockModel implements Teaser {

    @Self
    @Via(type = ResourceSuperType.class)
    private Teaser teaser;
    @Self
    protected SlingHttpServletRequest request;
    @OSGiService
    private ModelFactory modelFactory;

    @ValueMapValue
    private String buttonText;

    @ValueMapValue
    private String buttonLink;

    private String imageSrc;

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

    public String getImagePath() {
        if (imageSrc == null) {
            this.imageSrc = Optional.ofNullable(teaser.getImageResource())

                    .map(imageResource -> this.modelFactory.getModelFromWrappedRequest(this.request, imageResource,
                            Image.class))
                    .map(Image::getSrc)
                    .orElse(null);
        }
        return this.imageSrc;
    }

}
