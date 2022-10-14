package com.jolt.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {FooterModel.class},
        resourceType = {FooterModel.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FooterModel {
    protected static final String RESOURCE_TYPE = "jolt/components/footer";
    static final String PN_TOP_PAGES = "topPages";
    static final String PN_BOTTOM_PAGES = "bottomPages";

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    private String text;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean textIsRich;

    @ScriptVariable
    private Page currentPage;

    @ScriptVariable
    protected ValueMap properties;

    public String getText() {
        return text;
    }

    public boolean isRichText() {
        return textIsRich;
    }

    public java.util.List<HashMap<String, String>> getTopLinks() {
        List<HashMap<String, String>> links = new ArrayList<HashMap<String, String>>();
        List<Page> topLinks = getTopPages();
        if (topLinks == null || topLinks.isEmpty()) {
            return links;
        }

        for (Page page : topLinks) {
            if (page == null) {
                continue;
            }
            HashMap<String, String> hashMap = new HashMap<String, String>();
            String url = getUrl(page);
            String title = getTitle(page);
            hashMap.put("link", url);
            hashMap.put("title", title);
            links.add(hashMap);
        }

        return links;
    }

    public java.util.List<HashMap<String, String>> getBottomLinks() {
        List<HashMap<String, String>> links = new ArrayList<HashMap<String, String>>();
        List<Page> topLinks = getBottomPages();
        if (topLinks == null || topLinks.isEmpty()) {
            return links;
        }

        for (Page page : topLinks) {
            if (page == null) {
                continue;
            }
            HashMap<String, String> hashMap = new HashMap<String, String>();
            String url = getUrl(page);
            String title = getTitle(page);
            hashMap.put("link", url);
            hashMap.put("title", title);
            links.add(hashMap);
        }

        return links;
    }

    private String getUrl(Page page) {
        String vanityUrl = page.getVanityUrl();
        String url = StringUtils.isEmpty(vanityUrl)
                ? String.format("%s%s.html", request.getContextPath(), page.getPath())
                : String.format("%s%s", request.getContextPath(), vanityUrl);
        return url;
    }

    private String getTitle(Page page) {
        String title = page.getNavigationTitle();
        if (StringUtils.isEmpty(title)) {
            title = page.getPageTitle();
        }
        if (StringUtils.isEmpty(title)) {
            title = page.getTitle();
        }
        if (StringUtils.isEmpty(title)) {
            title = page.getName();
        }

        return title;
    }

    private java.util.List<Page> getTopPages() {
        // get the list item stream
        Stream<Page> itemStream = getTopListItems();

        // collect the results
        return itemStream.collect(Collectors.toList());
    }

    private java.util.List<Page> getBottomPages() {
        // get the list item stream
        Stream<Page> itemStream = getBottomListItems();

        // collect the results
        return itemStream.collect(Collectors.toList());
    }

    private Stream<Page> getTopListItems() {
        return Arrays.stream(this.properties.get(PN_TOP_PAGES, new String[0]))
                .map(this.currentPage.getPageManager()::getContainingPage)
                .filter(Objects::nonNull);
    }

    private Stream<Page> getBottomListItems() {
        return Arrays.stream(this.properties.get(PN_BOTTOM_PAGES, new String[0]))
                .map(this.currentPage.getPageManager()::getContainingPage)
                .filter(Objects::nonNull);
    }
}
