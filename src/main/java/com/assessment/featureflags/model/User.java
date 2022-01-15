package com.assessment.featureflags.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class User {
    private String userId;
    private String userName;
    private List<Feature> featureList;
}
