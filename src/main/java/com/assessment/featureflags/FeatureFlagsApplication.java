package com.assessment.featureflags;

import com.assessment.featureflags.model.Feature;
import com.assessment.featureflags.util.CommonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

import static com.assessment.featureflags.util.CommonUtil.*;

@SpringBootApplication
public class FeatureFlagsApplication {

	public static void main(String[] args) {

		SpringApplication.run(FeatureFlagsApplication.class, args);

		// Put data in global variable - "allFeatures"
		CommonUtil.putAllFeatures(new Feature(LIBRARY, EXPLICIT, false));
		CommonUtil.putAllFeatures(new Feature(BANK, EXPLICIT,false));
		CommonUtil.putAllFeatures(new Feature(TRANSPORT, EXPLICIT,false));
		CommonUtil.putAllFeatures(new Feature(VOTING, EXPLICIT,false));
		CommonUtil.putAllFeatures(new Feature(GLOBALFEATURE1, GLOBAL,false));
		CommonUtil.putAllFeatures(new Feature(GLOBALFEATURE2, GLOBAL,false));
		CommonUtil.putAllFeatures(new Feature(GLOBALFEATURE3, GLOBAL,false));

		List<Feature> user1Features = new ArrayList<>(); {
			user1Features.add(new Feature(LIBRARY, EXPLICIT, true));
			user1Features.add(new Feature(BANK, EXPLICIT, true));
			user1Features.add(new Feature(TRANSPORT, EXPLICIT, false));
			user1Features.add(new Feature(VOTING, EXPLICIT, false));
			user1Features.add(new Feature(GLOBALFEATURE1, GLOBAL, true));
		}

		List<Feature> user2Features = new ArrayList<>(); {
			user2Features.add(new Feature(LIBRARY, EXPLICIT, true));
			user2Features.add(new Feature(BANK, EXPLICIT, false));
			user2Features.add(new Feature(VOTING, EXPLICIT, false));
			user2Features.add(new Feature(GLOBALFEATURE1, GLOBAL, true));
			user2Features.add(new Feature(GLOBALFEATURE2, GLOBAL, true));
		}

		List<Feature> user3Features = new ArrayList<>(); {
			user3Features.add(new Feature(LIBRARY, EXPLICIT, true));
			user3Features.add(new Feature(VOTING, EXPLICIT, true));
			user3Features.add(new Feature(GLOBALFEATURE1, GLOBAL, true));
			user3Features.add(new Feature(GLOBALFEATURE2, GLOBAL, true));
			user3Features.add(new Feature(GLOBALFEATURE3, GLOBAL, true));
		}

		// Put data in global variable - "allUserFeatures"
		CommonUtil.putAllUserFeatures("1", user1Features);
		CommonUtil.putAllUserFeatures("2", user2Features);
		CommonUtil.putAllUserFeatures("3", user3Features);
	}
}