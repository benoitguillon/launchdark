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
import com.launchdarkly.client.LDUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class FeatureFlipping {

    @Bean
    public FeatureFlippingService featureFlippingService() {
        final LDClient client = new LDClient("xxxxxxxx");

        return new FeatureFlippingService() {
            @Override
            public boolean booleanValue(String key, boolean defaultValue) {
                return client.boolVariation(key, getCurrentLDUser(), defaultValue);
            }

            @Override
            public int intValue(String key, int defaultValue) {
                return client.intVariation(key, getCurrentLDUser(), defaultValue);
            }


            private LDUser getCurrentLDUser(){
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return new LDUser.Builder(userDetails.getUsername())
                        .email(userDetails.getUsername())
                        .build();
            }
        };

    }

}
