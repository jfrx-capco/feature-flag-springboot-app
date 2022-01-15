package com.assessment.featureflags.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Feature {
    private String featureName;
    private String featureType;
    private boolean isEnabled;
}
