// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.launchdark.launchdark;


import com.launchdarkly.client.LDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller()
public class Products {

    private static final Logger LOGGER = LoggerFactory.getLogger(Products.class);

    @Autowired
    private FeatureFlippingService featureFlippingService;

    @RequestMapping("/products")
    public String index(Model model) {
        int maxProducts = getMaxProducts();
        model.addAttribute("maxProducts", getMaxProducts());
        model.addAttribute("products", getProducts(maxProducts));
        model.addAttribute("user", getUserDetails().getUsername());
        model.addAttribute("showProductDetail", isShowProductDetail());
        model.addAttribute("fancyNewFeature", isFancyNewFeature());

        String toto = "max-products";


        return "catalog";
    }

    private boolean isFancyNewFeature() {
        return this.featureFlippingService.booleanValue("fancy-new-feature", false);
    }

    private List<Product> getProducts(int maxNumber) {
        List<Product> result = new ArrayList<>(maxNumber);
        for(int i=1; i<=maxNumber; i++) {
            result.add(new Product(String.valueOf(i), "Product " + i, i, i));
        }
        return result;
    }

    private UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean isShowProductDetail() {
        return this.featureFlippingService.booleanValue("show-product-detail", false);
    }

    private int getMaxProducts() {
        return this.featureFlippingService.intValue("max-products", 50);
    }


}
