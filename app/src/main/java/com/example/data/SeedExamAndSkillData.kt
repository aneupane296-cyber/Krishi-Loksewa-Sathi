package com.example.data

/**
 * Original copyright-free bilingual (English + Nepali Unicode) MCQs,
 * official PSC typing passages, and GD/Interview preparation items.
 */
object SeedExamAndSkillData {

    fun initialMcqs(): List<McqQuestionEntity> = listOf(
        McqQuestionEntity(
            id = "mcq_01",
            question = "नेपालको संविधान (२०७२) को कुन अनुसूचीमा 'कृषि तथा पशुपालन, कृषि उत्पादन व्यवस्थापन' स्थानीय तहको एकल अधिकार अन्तर्गत पर्दछ?",
            optionA = "अनुसूची ५ (Schedule 5)",
            optionB = "अनुसूची ६ (Schedule 6)",
            optionC = "अनुसूची ८ (Schedule 8)",
            optionD = "अनुसूची ९ (Schedule 9)",
            correctIndex = 2,
            explanation = "नेपालको संविधान २०७२ को अनुसूची ८ (Schedule 8) मा स्थानीय तहको अधिकार सूचीअन्तर्गत कृषि तथा पशुपालन, कृषि उत्पादन व्यवस्थापन र सहकारी समावेश छन्।",
            commissionKey = "FEDERAL",
            postLevelScope = "ALL",
            subjectTag = "Constitution & Acts",
            createdAt = 2001L
        ),
        McqQuestionEntity(
            id = "mcq_02",
            question = "What is the official tag color for Foundation Seed (मूल बीउ) under Nepal's seed certification standards?",
            optionA = "Golden Yellow",
            optionB = "White",
            optionC = "Blue",
            optionD = "Yellow",
            correctIndex = 1,
            explanation = "In Nepal's seed certification system: Breeder Seed = Golden Yellow, Foundation Seed = White, Certified Seed = Blue (1st gen) / Green (2nd gen), and Improved Seed = Yellow.",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            subjectTag = "Agronomy & Seed Tech",
            createdAt = 2002L
        ),
        McqQuestionEntity(
            id = "mcq_03",
            question = "Which chemical amendment is most appropriate for reclaiming a Sodic (Alkali) soil having ESP > 15% and pH > 8.5?",
            optionA = "Agricultural Lime (CaCO₃)",
            optionB = "Gypsum (CaSO₄·2H₂O)",
            optionC = "Urea",
            optionD = "Rock Phosphate",
            correctIndex = 1,
            explanation = "Gypsum supplies soluble calcium (Ca²⁺) which displaces excess exchangeable sodium (Na⁺) from clay colloids in sodic soils, allowing sodium sulfate to be leached away. Lime is used for acidic soils.",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            subjectTag = "Soil Science & IPNS",
            createdAt = 2003L
        ),
        McqQuestionEntity(
            id = "mcq_04",
            question = "गहुँ बालीमा सिँचाइको सबैभन्दा संवेदनशील अवस्था (Most critical stage of irrigation in wheat) कुन हो?",
            optionA = "गाँज आउने अवस्था (Tillering)",
            optionB = "मुकुट जरा निस्कने अवस्था (Crown Root Initiation - CRI)",
            optionC = "बाला निस्कने अवस्था (Heading)",
            optionD = "दाना दुधिलो हुने अवस्था (Milking)",
            correctIndex = 1,
            explanation = "गहुँ छरेको २१–२५ दिनमा आउने मुकुट जरा निस्कने अवस्था (CRI) सिँचाइका लागि सबैभन्दा संवेदनशील मानिन्छ।",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            subjectTag = "Agronomy & Seed Tech",
            createdAt = 2004L
        ),
        McqQuestionEntity(
            id = "mcq_05",
            question = "When did Nepal formally become the 147th member of the World Trade Organization (WTO)?",
            optionA = "April 23, 2004",
            optionB = "January 1, 1995",
            optionC = "May 15, 2008",
            optionD = "September 20, 2015",
            correctIndex = 0,
            explanation = "Nepal acceded to the WTO on April 23, 2004 (11 Baisakh 2061 BS), becoming the 147th member and the first LDC admitted through the full working-party negotiation process.",
            commissionKey = "FEDERAL",
            postLevelScope = "OFFICER",
            subjectTag = "Plans & Policies",
            createdAt = 2005L
        ),
        McqQuestionEntity(
            id = "mcq_06",
            question = "Which commercial vegetative propagation method is standard for Litchi (लिची) in Nepal?",
            optionA = "Veneer grafting",
            optionB = "T-budding",
            optionC = "Air layering (Gootee)",
            optionD = "Cleft grafting",
            correctIndex = 2,
            explanation = "Air layering (Gootee / Marcottage) during monsoon is the standard commercial propagation method for Litchi, whereas Mango uses Veneer/Stone grafting and Citrus uses T-budding.",
            commissionKey = "MADHESH",
            postLevelScope = "ALL",
            subjectTag = "Horticulture",
            createdAt = 2006L
        ),
        McqQuestionEntity(
            id = "mcq_07",
            question = "मकै बालीको अमेरिकन फौजी कीरा (Fall Armyworm - Spodoptera frugiperda) को लार्भा पहिचान गर्ने मुख्य चिन्ह के हो?",
            optionA = "टाउकोमा उल्टो 'Y' आकार र पुच्छरतिरको आठौँ खण्डमा वर्गाकार ४ थोप्ला",
            optionB = "शरीरभरि लामो सेतो रौँ हुनु",
            optionC = "पाँखुरामा रातो धर्का हुनु",
            optionD = "गुभोमा पात बेर्ने मात्र लक्षण",
            correctIndex = 0,
            explanation = "अमेरिकन फौजी कीरा (Spodoptera frugiperda) को लार्भाको टाउकोमा स्पष्ट उल्टो 'Y' चिन्ह र आठौँ उदर खण्डमा वर्गाकार रूपमा रहेका ४ वटा काला थोप्ला (pinacula) हुन्छन्।",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            subjectTag = "Plant Protection",
            createdAt = 2007L
        ),
        McqQuestionEntity(
            id = "mcq_08",
            question = "In Everett Rogers' Diffusion of Innovations model, what percentage of a social system represents 'Early Adopters'?",
            optionA = "2.5%",
            optionB = "13.5%",
            optionC = "34.0%",
            optionD = "16.0%",
            correctIndex = 1,
            explanation = "Rogers' adopter curve divides populations into Innovators (2.5%), Early Adopters (13.5%), Early Majority (34%), Late Majority (34%), and Laggards (16%). Early Adopters hold the highest opinion leadership.",
            commissionKey = "ALL",
            postLevelScope = "OFFICER",
            subjectTag = "Extension & Economics",
            createdAt = 2008L
        ),
        McqQuestionEntity(
            id = "mcq_09",
            question = "१% बोर्डो मिक्सर (1% Bordeaux Mixture) १०० लिटर बनाउन निलो तुथो (CuSO₄) र चुन (CaO) कति अनुपातमा मिसाइन्छ?",
            optionA = "०.५ केजी निलो तुथो : १ केजी चुन : १०० लिटर पानी",
            optionB = "१ केजी निलो तुथो : १ केजी चुन : १०० लिटर पानी",
            optionC = "२ केजी निलो तुथो : १ केजी चुन : १०० लिटर पानी",
            optionD = "१ केजी निलो तुथो : २ केजी चुन : ५० लिटर पानी",
            correctIndex = 1,
            explanation = "१% बोर्डो मिक्सरको मानक अनुपात १:१:१०० हो अर्थात् १ केजी निलो तुथो, १ केजी चुन र १०० लिटर पानी प्रयोग गरी गैर-धातु भाँडोमा तयार गरिन्छ।",
            commissionKey = "ALL",
            postLevelScope = "JT_JTA",
            subjectTag = "Subjective & Practical",
            createdAt = 2009L
        ),
        McqQuestionEntity(
            id = "mcq_10",
            question = "Which province of Nepal officially adopted an 'Organic Agriculture Province' mission and is renowned for Jumli Marsi rice and Raithane crops?",
            optionA = "Gandaki Province",
            optionB = "Lumbini Province",
            optionC = "Karnali Province",
            optionD = "Sudurpashchim Province",
            correctIndex = 2,
            explanation = "Karnali Province adopted the Organic Karnali Agriculture Mission, highlighting high-altitude Jumli Marsi rice, apples, walnuts, and indigenous Raithane crops (chino, kaguno, buckwheat, beans).",
            commissionKey = "KARNALI",
            postLevelScope = "ALL",
            subjectTag = "Provincial Flagships",
            createdAt = 2010L
        ),
        McqQuestionEntity(
            id = "mcq_11",
            question = "Under WHO pesticide toxicity classification, what color band represents 'Moderately Hazardous' (Class II) pesticides?",
            optionA = "Red",
            optionB = "Yellow",
            optionC = "Blue",
            optionD = "Green",
            correctIndex = 1,
            explanation = "WHO hazard color bands are: Red (Class Ia/Ib Extremely/Highly Hazardous), Yellow (Class II Moderately Hazardous), Blue (Class III Slightly Hazardous), and Green (Class U Unlikely to present acute hazard).",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            subjectTag = "Plant Protection",
            createdAt = 2011L
        ),
        McqQuestionEntity(
            id = "mcq_12",
            question = "A soil organic residue with a Carbon-to-Nitrogen (C:N) ratio greater than 30:1 typically results in:",
            optionA = "Rapid net mineralization of nitrogen",
            optionB = "Net immobilization (temporary tie-up) of soil nitrogen",
            optionC = "Immediate soil salinization",
            optionD = "Biological nitrogen fixation by Rhizobium",
            correctIndex = 1,
            explanation = "When organic matter has a wide C:N ratio (> 30:1, such as raw cereal straw), soil microbes scavenge available soil inorganic N to decompose the carbon, causing temporary N immobilization.",
            commissionKey = "ALL",
            postLevelScope = "OFFICER",
            subjectTag = "Soil Science & IPNS",
            createdAt = 2012L
        ),
        McqQuestionEntity(
            id = "mcq_13",
            question = "नेपाल कृषि अनुसन्धान परिषद् (NARC) को स्थापना कहिले भएको हो?",
            optionA = "वि.सं. १९८१",
            optionB = "वि.सं. २०४५",
            optionC = "वि.सं. २०४८",
            optionD = "वि.सं. २०६४",
            correctIndex = 2,
            explanation = "नेपाल कृषि अनुसन्धान परिषद् (NARC) को स्थापना नेपाल कृषि अनुसन्धान परिषद् ऐन, २०४८ अन्तर्गत वि.सं. २०४८ (1991 AD) मा भएको हो। कृषि अड्डा/विभागको सुरुवात वि.सं. १९८१ मा भएको थियो।",
            commissionKey = "FEDERAL",
            postLevelScope = "ALL",
            subjectTag = "Plans & Policies",
            createdAt = 2013L
        ),
        McqQuestionEntity(
            id = "mcq_14",
            question = "Which financial appraisal criterion indicates that an agricultural investment project is economically viable?",
            optionA = "B/C Ratio < 1 and NPV < 0",
            optionB = "B/C Ratio > 1, NPV > 0, and IRR > Cost of Capital",
            optionC = "IRR < Bank Interest Rate",
            optionD = "Marginal Physical Product < 0",
            correctIndex = 1,
            explanation = "A project is financially acceptable when Benefit-Cost Ratio exceeds 1 (B/C > 1), Net Present Value is positive (NPV > 0), and Internal Rate of Return exceeds the opportunity cost of capital.",
            commissionKey = "ALL",
            postLevelScope = "OFFICER",
            subjectTag = "Extension & Economics",
            createdAt = 2014L
        ),
        McqQuestionEntity(
            id = "mcq_15",
            question = "Which pheromone lure is specifically used to trap Fruit Flies (Bactrocera dorsalis / B. zonata) in orchards?",
            optionA = "Helilure",
            optionB = "Lucinlure",
            optionC = "Methyl Eugenol",
            optionD = "Tutalure",
            correctIndex = 2,
            explanation = "Methyl Eugenol attracts male Bactrocera fruit flies in mango, guava, and citrus orchards. Helilure targets Helicoverpa, Lucinlure targets brinjal shoot & fruit borer, and Tutalure targets Tuta absoluta.",
            commissionKey = "ALL",
            postLevelScope = "JT_JTA",
            subjectTag = "Plant Protection",
            createdAt = 2015L
        ),
        McqQuestionEntity(
            id = "mcq_16",
            question = "Which of the following is a Non-Climacteric fruit that does NOT ripen further after harvesting?",
            optionA = "Mango",
            optionB = "Banana",
            optionC = "Citrus (Mandarin / Sweet Orange)",
            optionD = "Papaya",
            correctIndex = 2,
            explanation = "Citrus, Litchi, Grape, Pomegranate, and Strawberry are non-climacteric fruits — they must be harvested at full physiological maturity on the tree.",
            commissionKey = "BAGMATI",
            postLevelScope = "ALL",
            subjectTag = "Horticulture",
            createdAt = 2016L
        )
    )

    fun typingPassages(): List<TypingPassage> = listOf(
        TypingPassage(
            id = "en_ads",
            title = "Agriculture Development Strategy (English)",
            language = TypingLanguage.ENGLISH,
            content = "The Agriculture Development Strategy guides Nepal toward a self reliant sustainable competitive and inclusive agricultural sector. Its four strategic pillars are governance productivity profitable commercialization and competitiveness. Strengthening research extension linkages quality seed certification year round irrigation and soil organic matter management helps smallholder farmers increase crop yields across mountain hill and terai regions."
        ),
        TypingPassage(
            id = "en_ipm",
            title = "Conservation Agriculture & IPM (English)",
            language = TypingLanguage.ENGLISH,
            content = "Conservation agriculture rests on minimum soil disturbance permanent organic residue cover and scientific crop rotation with legumes. Integrated pest management combines resistant varieties cultural sanitation pheromone traps and biological agents like Trichoderma to protect rice wheat maize and horticultural crops while safeguarding beneficial insects soil health and consumer food safety."
        ),
        TypingPassage(
            id = "np_krishi",
            title = "नेपालको कृषि विकास र खाद्य सम्प्रभुता (नेपाली युनिकोड)",
            language = TypingLanguage.NEPALI,
            content = "नेपालको संविधानले प्रत्येक नागरिकलाई खाद्य सम्प्रभुताको हक सुनिश्चित गरेको छ। दिगो कृषि विकासका लागि गुणस्तरीय बीउ प्रमाणीकरण, सन्तुलित माटो व्यवस्थापन, एकीकृत शत्रुजीव व्यवस्थापन र सिँचाइ विस्तार आवश्यक पर्दछ। संघ, प्रदेश र स्थानीय तहको समन्वयबाट किसानको उत्पादन र उत्पादकत्व वृद्धि गरी कृषि क्षेत्रलाई व्यावसायिक र आत्मनिर्भर बनाउन सकिन्छ।"
        ),
        TypingPassage(
            id = "np_seed",
            title = "बीउ प्रमाणीकरण र प्राङ्गारिक खेती (नेपाली युनिकोड)",
            language = TypingLanguage.NEPALI,
            content = "नेपालमा बीउ बिजन ऐन अनुसार प्रजनक बीउ, मूल बीउ, प्रमाणित बीउ र उन्नत बीउको गुणस्तर नियन्त्रण गरिन्छ। पहाडी तथा हिमाली क्षेत्रमा रैथाने बालीको संरक्षण र प्राङ्गारिक कृषि प्रवर्द्धनले उच्च मूल्यका उत्पादन निर्यात गर्न मद्दत गर्दछ। कृषि प्राविधिकहरूले खेतबारीमै पुगेर किसानलाई वैज्ञानिक प्रविधि सिकाउनु लोक सेवाको प्रमुख दायित्व हो।"
        )
    )

    fun initialGdChecklist(): List<GdChecklistEntity> = listOf(
        GdChecklistEntity(
            id = "gd_01",
            category = "Group Discussion (GD)",
            title = "Structure 60-Second Opening Remarks (Fact -> Issue -> Solution)",
            guidance = "Anchor your opening on ADS 2015–2035 metrics, Constitutional Schedules 5–9, or current agro-trade deficit data rather than vague opinions.",
            sortOrder = 1
        ),
        GdChecklistEntity(
            id = "gd_02",
            category = "Group Discussion (GD)",
            title = "Prepare 5 Hot Nepal Agriculture GD Topics",
            guidance = "Practice pros/cons on: (1) Chemical vs. Organic Transition, (2) Youth Migration & Mechanization, (3) Fertilizer Supply Chain Reform, (4) Climate-Smart Agriculture, (5) WTO & Agro-Import Substitution.",
            sortOrder = 2
        ),
        GdChecklistEntity(
            id = "gd_03",
            category = "Group Discussion (GD)",
            title = "Active Listening & Respectful Counter-Arguments",
            guidance = "Acknowledge peers by point ('Building on the previous point about SPS labs...'), avoid interrupting, and summarize consensus if the panel invites closure.",
            sortOrder = 3
        ),
        GdChecklistEntity(
            id = "gd_04",
            category = "Group Discussion (GD)",
            title = "Maintain Balanced Federal–Provincial–Local Perspective",
            guidance = "Clarify which tier executes extension (Local Palika), labs/value chains (Province), and quarantine/policy (Federal) to show administrative maturity.",
            sortOrder = 4
        ),
        GdChecklistEntity(
            id = "iv_01",
            category = "Interview & Technical Viva",
            title = "Master Home District & Selected Province Flagship Crops",
            guidance = "Be ready to explain major agro-climatic zones, PMAMP zones/superzones, dominant cropping patterns, and top 2 pest/soil bottlenecks in your applied province.",
            sortOrder = 5
        ),
        GdChecklistEntity(
            id = "iv_02",
            category = "Interview & Technical Viva",
            title = "Review Core Acts & Field Calculations",
            guidance = "Revise Seeds Act 2045, Pesticides Management Act 2076, Public Procurement Act 2063, fertilizer dose calculation (N-P-K to Urea/DAP/MOP), and 1% Bordeaux mixture steps.",
            sortOrder = 6
        ),
        GdChecklistEntity(
            id = "iv_03",
            category = "Interview & Technical Viva",
            title = "Civil Service Ethics & Situational Problem Solving",
            guidance = "Prepare calm, lawful responses for field dilemmas: handling subsidy pressure, responding to sudden pest outbreaks (e.g., Fall Armyworm), and transparent procurement.",
            sortOrder = 7
        ),
        GdChecklistEntity(
            id = "iv_04",
            category = "Interview & Technical Viva",
            title = "Document Folder & Attire Verification",
            guidance = "Organize original citizenship, academic transcripts, equivalence certificates, council/training records, and PSC admit card neatly; dress cleanly and formally.",
            sortOrder = 8
        )
    )
}
