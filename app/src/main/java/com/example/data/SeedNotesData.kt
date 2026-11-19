package com.example.data

/**
 * Original, copyright-free public-domain summaries compiled from official Nepal PSC syllabi,
 * the Constitution of Nepal (2072), public agricultural acts, and provincial programs.
 */
object SeedNotesData {

    fun initialNotes(): List<StudyNoteEntity> = listOf(
        // 1. Constitution of Nepal (2072) & Agricultural Acts
        StudyNoteEntity(
            id = "seed_const_acts",
            title = "Constitution of Nepal (2072) & Core Agriculture Acts",
            commissionKey = "FEDERAL",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Constitution & Acts",
            overview = "Essential constitutional division of agricultural jurisdiction across Federal, Provincial, and Local tiers (Schedules 5–9) alongside Nepal's primary statutory acts governing seeds, quarantine, pesticides, food sovereignty, and public procurement.",
            sections = listOf(
                NoteSection(
                    subheading = "Constitutional Schedules (2072 BS)",
                    bullets = listOf(
                        "Schedule 5 (Federal): Central policies, national quarantine standards, international trade treaties, and federal agriculture research.",
                        "Schedule 6 (Provincial): Provincial agriculture and livestock development, provincial cooperatives, land management, and irrigation.",
                        "Schedule 7 (Concurrent Federal & Provincial): Scientific research, supply/distribution of essential goods, and environment conservation.",
                        "Schedule 8 (Local Level): Local agriculture & animal husbandry, agro-products management, cooperatives, and local market management.",
                        "Schedule 9 (Concurrent Federal, Provincial & Local): Agriculture, cooperatives, disaster management, and biodiversity."
                    )
                ),
                NoteSection(
                    subheading = "Key Agricultural & Governance Acts of Nepal",
                    bullets = listOf(
                        "Seeds Act, 2045 (1988 AD): Establishes the National Seed Board (NSB), regulates seed quality, variety release/registration, and certification.",
                        "Plant Protection Act, 2064 (2007 AD): Mandates NPPO (National Plant Protection Organization), phytosanitary certificates, and import/export quarantine permits.",
                        "Pesticides Management Act, 2076 (2019 AD): Replaced the 2048 Act; regulates pesticide registration, licensing, safe disposal, and strict penalties for banned chemicals.",
                        "Right to Food and Food Sovereignty Act, 2075 (2018 AD): Operationalizes Article 36 fundamental right to food, farmer classification, and food reserve mechanisms.",
                        "Public Procurement Act, 2063 (2007 AD): Governs transparent bidding, sealed quotations, cost estimates, and contract administration for public agri-programs."
                    )
                )
            ),
            quickFact = "Article 36 of the Constitution of Nepal (2072) guarantees every citizen the Right to Food and Food Sovereignty, enacted via the 2075 BS Act.",
            createdAt = 1001L
        ),

        // 2. Plans, Policies & Institutions
        StudyNoteEntity(
            id = "seed_plans_policies",
            title = "ADS (2015–2035), National Institutions & WTO",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Plans & Policies",
            overview = "Macro-policy framework steering Nepal's agricultural transformation from subsistence farming toward a self-reliant, sustainable, competitive, and inclusive sector.",
            sections = listOf(
                NoteSection(
                    subheading = "Agriculture Development Strategy (ADS 2015–2035) — 4 Strategic Pillars",
                    bullets = listOf(
                        "1. Governance: Strengthened policy coordination, decentralization, food safety standards, and credible monitoring.",
                        "2. Productivity: Improved soil fertility, year-round irrigation, quality seeds/breeds, research-extension linkage (NARC-DoA-Local).",
                        "3. Profitable Commercialization: Value chains, agribusiness incubation, rural roads, cold storage, and access to agricultural credit & insurance.",
                        "4. Competitiveness: Market intelligence, sanitary & phytosanitary (SPS) compliance, and export promotion of high-value niche crops."
                    )
                ),
                NoteSection(
                    subheading = "Institutional Milestones & Policy Provisions",
                    bullets = listOf(
                        "Department of Agriculture (DoA): Traces its origin to 1981 BS (Krishi Adda established in Charkhal, Kathmandu).",
                        "Nepal Agricultural Research Council (NARC): Established as an autonomous apex research body in 2048 BS (1991 AD) under the NARC Act.",
                        "Crop & Livestock Insurance Subsidy: Government of Nepal provides up to 80% premium subsidy to shield farmers from climatic and biological shocks.",
                        "WTO Accession: Nepal became the 147th member of the World Trade Organization on April 23, 2004 (11 Baisakh 2061 BS), governed by AoA and SPS agreements."
                    )
                )
            ),
            quickFact = "Nepal joined the WTO on April 23, 2004 (147th member) — the first Least Developed Country (LDC) to join through the full working-party negotiation process.",
            createdAt = 1002L
        ),

        // 3. Agronomy & Seed Technology
        StudyNoteEntity(
            id = "seed_agronomy_seed",
            title = "Agronomy, Conservation Agriculture & Seed Certification",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Agronomy & Seed Tech",
            overview = "Core field crop agronomy principles, resource-conserving tillage, System of Rice Intensification (SRI), Nepal's official seed tag color system, and staple crop management norms.",
            sections = listOf(
                NoteSection(
                    subheading = "Conservation Agriculture (CA) & SRI",
                    bullets = listOf(
                        "3 Core Principles of CA: (1) Minimum mechanical soil disturbance (zero/reduced tillage), (2) Permanent organic soil cover (crop residue/mulch), (3) Crop diversification/rotation (including legumes).",
                        "Zero-Till Wheat (Terai Rice-Wheat System): Advances wheat sowing by 10–15 days after rice harvest, avoids terminal heat stress, cuts tillage cost by ~30–40%, and reduces Phalaris minor emergence.",
                        "System of Rice Intensification (SRI): Transplant young single seedlings (8–12 days old) at wide square spacing (25×25 cm), keep soil moist (alternate wetting and drying), mechanical cono-weeding, and rich organic matter."
                    )
                ),
                NoteSection(
                    subheading = "Nepal Seed Certification Classes & Tag Colors",
                    bullets = listOf(
                        "Breeder Seed (प्रजनक बीउ): Golden Yellow / Brownish-Yellow tag — produced under direct supervision of originating plant breeder.",
                        "Foundation Seed (मूल बीउ): White tag — progeny of breeder seed maintaining high genetic purity.",
                        "Certified Seed (प्रमाणित बीउ): Tag with Blue border (1st generation) or Green border (2nd generation) — verified by SQCC/seed labs.",
                        "Improved / Truthfully Labelled Seed (उन्नत बीउ): Yellow tag — widely distributed for commercial farmer sowing."
                    )
                ),
                NoteSection(
                    subheading = "Staple Crop Seed Rates & Critical Irrigation Stages",
                    bullets = listOf(
                        "Rice (Transplanted): 40–50 kg/ha (Direct seeded: 70–80 kg/ha; SRI: 5–8 kg/ha). Critical irrigation: Active tillering, Panicle initiation (PI), Flowering/Heading.",
                        "Wheat: 120 kg/ha (late sowing up to 140 kg/ha). Most critical irrigation: Crown Root Initiation (CRI, 21 DAS), followed by Jointing, Flowering, and Milking.",
                        "Maize: 20–25 kg/ha. Critical irrigation: Knee-high stage, Tasseling, and Silking (moisture stress at tasseling/silking severely cuts grain set)."
                    )
                )
            ),
            quickFact = "Crown Root Initiation (CRI) at 20–25 days after sowing (DAS) is the single most critical irrigation stage in wheat.",
            createdAt = 1003L
        ),

        // 4. Soil Science & IPNS
        StudyNoteEntity(
            id = "seed_soil_ipns",
            title = "Soil Science, Problem Soil Reclamation & IPNS",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Soil Science & IPNS",
            overview = "Diagnostic criteria and amendments for acidic, saline, and sodic soils in Nepal's Hills and Terai, essential plant nutrition, organic matter decomposition, and biological nitrogen/phosphorus bio-fertilizers.",
            sections = listOf(
                NoteSection(
                    subheading = "Problem Soils & Reclamation Measures",
                    bullets = listOf(
                        "Acidic Soils (pH < 6.5, common in Mid-Hills & Eastern Terai): Reclaim with Agricultural Lime (CaCO₃ / Dolomite), apply organic manure, and select acid-tolerant crops (tea, potato).",
                        "Saline Soils (EC > 4 dS/m, ESP < 15%, pH < 8.5): Excess soluble neutral salts; reclaim via leaching with good-quality irrigation water and proper subsurface drainage.",
                        "Sodic / Alkali Soils (EC < 4 dS/m, ESP > 15%, pH > 8.5): Dispersed clay structure due to exchangeable Na⁺; reclaim by applying Gypsum (CaSO₄·2H₂O) followed by leaching."
                    )
                ),
                NoteSection(
                    subheading = "17 Essential Nutrients & C:N Ratio Rules",
                    bullets = listOf(
                        "17 Essential Elements (Arnon & Stout criteria): Basic (C, H, O); Primary Macro (N, P, K); Secondary Macro (Ca, Mg, S); Micro (Fe, Mn, Zn, Cu, B, Mo, Cl, Ni).",
                        "C:N Ratio < 20:1 -> Net Mineralization (rapid nutrient release to crops, e.g., legume residues, well-rotted FYM).",
                        "C:N Ratio 20:1 to 30:1 -> Equilibrium (minimal net immobilization or mineralization).",
                        "C:N Ratio > 30:1 -> Net Immobilization (microbes tie up available soil N, e.g., raw wheat/rice straw at 60–80:1)."
                    )
                ),
                NoteSection(
                    subheading = "Bio-Fertilizers in Integrated Plant Nutrient System (IPNS)",
                    bullets = listOf(
                        "Rhizobium: Symbiotic N₂ fixer in root nodules of legumes (lentil, soybean, black gram).",
                        "Azotobacter & Azospirillum: Free-living / associative N₂ fixers used in cereals, maize, and vegetables.",
                        "PSB (Phosphate Solubilizing Bacteria, e.g., Pseudomonas, Bacillus): Solubilizes fixed soil phosphorus into plant-available forms.",
                        "VAM (Vesicular Arbuscular Mycorrhiza): Fungal symbiosis expanding root surface area for P, Zn, and water uptake."
                    )
                )
            ),
            quickFact = "Nickel (Ni) is the 17th essential plant nutrient, essential for urease enzyme activity; Gypsum (CaSO₄·2H₂O) is the benchmark chemical amendment for sodic soils.",
            createdAt = 1004L
        ),

        // 5. Horticulture & Post-Harvest Technology
        StudyNoteEntity(
            id = "seed_horticulture",
            title = "Horticulture Propagation, HDP, TPS & Post-Harvest",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Horticulture",
            overview = "Commercial vegetative propagation methods for Nepal's major fruit species, orchard intensification, True Potato Seed technology, ripening physiology, and low-cost evaporative cooling storage.",
            sections = listOf(
                NoteSection(
                    subheading = "Commercial Propagation Methods",
                    bullets = listOf(
                        "Litchi (लिची): Air layering (Gootee / Marcottage) during monsoon (Ashadh–Shrawan) with IBA rooting hormone.",
                        "Mango (आँप): Veneer grafting and Stone/Epicotyl grafting for uniform, true-to-type commercial saplings.",
                        "Citrus (सुन्तला/कागती): Shield / T-budding on Trifoliate orange (Poncirus trifoliata) or rough lemon rootstock for disease tolerance.",
                        "Apple & Walnut (Temperate): Tongue / Whip grafting and Cleft grafting during dormant winter season."
                    )
                ),
                NoteSection(
                    subheading = "Orchard & Vegetable Technologies",
                    bullets = listOf(
                        "High Density Planting (HDP): Planting dwarf/semi-dwarf rootstocks (e.g., M9/MM106 in apple, Amrapali mango at 2.5×2.5 m) for early bearing, higher yield/ha, and easy canopy management.",
                        "True Potato Seed (TPS): Botanical seed from potato berries; needs only ~100–150 g/ha (vs. 1.5–2.0 t/ha seed tubers), lowers transport cost, and minimizes tuber-borne viruses."
                    )
                ),
                NoteSection(
                    subheading = "Ripening Physiology & Zero Energy Cool Chamber (ZECC)",
                    bullets = listOf(
                        "Climacteric Fruits (respiration & ethylene surge post-harvest; ripen after picking): Mango, Banana, Apple, Papaya, Guava, Tomato, Avocado.",
                        "Non-Climacteric Fruits (must mature on plant; no post-harvest ripening burst): Citrus, Grape, Litchi, Pomegranate, Strawberry, Pineapple.",
                        "ZECC (Pusa Zero Energy Cool Chamber): Double-wall brick structure with damp river sand between walls; evaporative cooling lowers temperature by 10–15°C and maintains ~90% RH without electricity."
                    )
                )
            ),
            quickFact = "TPS reduces potato planting material requirement from ~2,000 kg tubers/ha down to just 100–150 grams of true botanical seed per hectare.",
            createdAt = 1005L
        ),

        // 6. Plant Protection & IPM
        StudyNoteEntity(
            id = "seed_plant_protection",
            title = "Plant Protection, Major Pests, Diseases & IPM",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Plant Protection",
            overview = "Diagnostic symptoms and Integrated Pest Management (IPM) for high-priority insect pests and crop diseases in Nepal, pheromone trapping, biological agents, and WHO pesticide toxicity color bands.",
            sections = listOf(
                NoteSection(
                    subheading = "Major Insect Pests & Pheromone Lures",
                    bullets = listOf(
                        "Rice Yellow Stem Borer (Scirpophaga incertulas): Causes 'Dead Heart' in vegetative stage and 'White Ear Head' at reproductive stage.",
                        "Brown Planthopper (Nilaparvata lugens): Sucks phloem sap from stem base causing circular drying patches known as 'Hopper Burn'.",
                        "Fall Armyworm (Spodoptera frugiperda): Invasive maize pest showing inverted 'Y' on head capsule, four square spots on 8th abdominal segment, and sawdust-like frass in whorl.",
                        "Key Pheromone Lures: Helilure (Helicoverpa armigera), Lucinlure (Brinjal Shoot & Fruit Borer, Leucinodes orbonalis), Methyl Eugenol (Bactrocera fruit flies), Tutalure (Tuta absoluta tomato leafminer)."
                    )
                ),
                NoteSection(
                    subheading = "Major Diseases & Bio-Control Agents",
                    bullets = listOf(
                        "Rice Blast (Pyricularia oryzae / Magnaporthe oryzae): Spindle/boat-shaped lesions with gray center and brown margin; causes neck blast.",
                        "Potato Late Blight (Phytophthora infestans): Water-soaked dark lesions with white cottony growth on leaf underside in cool, humid fog.",
                        "Citrus Greening / Huanglongbing (Candidatus Liberibacter): Phloem-limited bacterium vectored by Asian Citrus Psylla (Diaphorina citri); causes blotchy mottle & lopsided bitter fruits.",
                        "Bio-Control Agents: Trichoderma viride/harzianum (antagonistic fungus against soil-borne Fusarium/Pythium wilt), Beauveria bassiana & Metarhizium (entomopathogenic fungi), NPV (Nuclear Polyhedrosis Virus)."
                    )
                ),
                NoteSection(
                    subheading = "WHO Pesticide Toxicity Label Colors",
                    bullets = listOf(
                        "Red (Class Ia / Ib): Extremely / Highly Toxic — Poison (skull & crossbones); strictly restricted/banned categories.",
                        "Yellow (Class II): Moderately Hazardous — Danger.",
                        "Blue (Class III): Slightly Hazardous — Caution.",
                        "Green (Class U): Unlikely to present acute hazard in normal use — Safe/Low toxicity."
                    )
                )
            ),
            quickFact = "Inverted 'Y' marking on the head and 4 pinacula dots in a square on the 8th abdominal segment uniquely identify Fall Armyworm (Spodoptera frugiperda) larvae.",
            createdAt = 1006L
        ),

        // 7. Agricultural Extension & Economics
        StudyNoteEntity(
            id = "seed_extension_econ",
            title = "Agricultural Extension, PRA & Project Economics",
            commissionKey = "ALL",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Extension & Economics",
            overview = "Foundational communication theory, innovation diffusion curves, Participatory Rural Appraisal (PRA) field tools, production economics, and financial project appraisal metrics.",
            sections = listOf(
                NoteSection(
                    subheading = "Communication & Diffusion of Innovations",
                    bullets = listOf(
                        "J.P. Leagans 6-Element Communication Model: Communicator -> Message -> Channel -> Treatment of Message -> Audience -> Audience Response (Feedback).",
                        "Everett Rogers 5 Adopter Categories: Innovators (2.5%), Early Adopters (13.5%), Early Majority (34%), Late Majority (34%), Laggards (16%).",
                        "PRA Tools: Social & Resource Mapping, Seasonal Calendar, Transect Walk, Venn (Chapati) Diagram, Wealth Ranking, Timeline, and Matrix Ranking."
                    )
                ),
                NoteSection(
                    subheading = "Production Economics & Financial Appraisal Criteria",
                    bullets = listOf(
                        "Law of Diminishing Marginal Returns: Adding successive units of a variable input to fixed inputs eventually causes Marginal Physical Product (MPP) to decline. Rational production occurs in Zone II (where elasticity of production 0 ≤ Ep ≤ 1).",
                        "Benefit-Cost Ratio (B/C Ratio): Present worth of benefits ÷ Present worth of costs. Project is financially viable when B/C > 1.",
                        "Net Present Value (NPV): Sum of discounted net cash flows. Accept project when NPV > 0.",
                        "Internal Rate of Return (IRR): Discount rate where NPV = 0. Accept project when IRR > Cost of Capital (Opportunity Cost)."
                    )
                )
            ),
            quickFact = "Rational farm production always operates in Stage II of the classical production function, where Average Physical Product (APP) ≥ Marginal Physical Product (MPP) ≥ 0.",
            createdAt = 1007L
        ),

        // 8A–8G: All 7 Provinces Flagship Commodity Pockets & Programs
        StudyNoteEntity(
            id = "seed_prov_koshi",
            title = "Koshi Province — Agricultural Flagships & Commodity Pockets",
            commissionKey = "KOSHI",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Provincial Flagships",
            overview = "Agro-ecological strengths of Koshi Province spanning eastern Terai grain baskets (Jhapa, Morang, Sunsari) and high-value eastern hill export commodities (Ilam, Panchthar, Taplejung, Dhankuta).",
            sections = listOf(
                NoteSection(
                    subheading = "Signature Commodity Pockets & Superzones",
                    bullets = listOf(
                        "Large Cardamom (अलैँची): Taplejung, Panchthar, Ilam, Sankhuwasabha — Nepal's premier spice export belt.",
                        "Orthodox & CTC Tea (चिया): Ilam, Jhapa, Panchthar, Dhankuta (famous 'A-O-A-K' cash crops of Ilam: Alainchi, Olan/Milk, Aduwa, Kucho, Akabare, Kiwi, Tea).",
                        "Rice Superzone & Commercial Banana/Supari: Jhapa & Morang; Avocado & Orange Hub: Dhankuta; Akabare Chili & Kiwi: Ilam & Tehrathum."
                    )
                ),
                NoteSection(
                    subheading = "Provincial Priorities",
                    bullets = listOf(
                        "Export-oriented value chain labs, cardamom dryer modernization, tea auction/branding, and citrus decline management across eastern mid-hills."
                    )
                )
            ),
            quickFact = "Jhapa district hosts one of Nepal's first Rice Superzones under PMAMP, while Taplejung leads national Large Cardamom production.",
            createdAt = 1008L
        ),

        StudyNoteEntity(
            id = "seed_prov_madhesh",
            title = "Madhesh Province — Agricultural Flagships & Commodity Pockets",
            commissionKey = "MADHESH",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Provincial Flagships",
            overview = "Madhesh Province represents Nepal's flat fertile Indo-Gangetic plain, driving national food security in rice, wheat, fish, sugarcane, mango, and winter vegetables.",
            sections = listOf(
                NoteSection(
                    subheading = "Signature Commodity Pockets & Hubs",
                    bullets = listOf(
                        "Fish Superzone (माछा सुपरजोन): Bara & Rautahat (major warm-water carp & pangasius aquaculture belt).",
                        "Mango & Litchi Belt: Siraha, Saptari, Sarlahi, Mahottari (Malda, Bombay, Dashahari mangoes).",
                        "Sugarcane & Oilseeds: Sarlahi, Mahottari, Rautahat, Bara; Rice & Wheat grain bowl across all 8 districts."
                    )
                ),
                NoteSection(
                    subheading = "Provincial Priorities",
                    bullets = listOf(
                        "Shallow/deep tube-well solar irrigation, zero-till wheat mechanization, commercial fishery cold chains, and soil organic carbon restoration."
                    )
                )
            ),
            quickFact = "Bara district is nationally renowned for its Fish Superzone and commercial vegetable corridors supplying Kathmandu and regional markets.",
            createdAt = 1009L
        ),

        StudyNoteEntity(
            id = "seed_prov_bagmati",
            title = "Bagmati Province — Agricultural Flagships & Commodity Pockets",
            commissionKey = "BAGMATI",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Provincial Flagships",
            overview = "Bagmati Province combines peri-urban high-intensity vegetable, poultry, and dairy hubs around Kathmandu Valley and Chitwan with unique temperate fruit pockets.",
            sections = listOf(
                NoteSection(
                    subheading = "Signature Commodity Pockets & Hubs",
                    bullets = listOf(
                        "Sweet Orange (जुनार): Sindhuli & Ramechhap (GI-potential flagship citrus belt).",
                        "Potato & Commercial Vegetables: Kavrepalanchok, Dhading, Makwanpur (Daman/Palung), Bhaktapur.",
                        "Poultry, Dairy, Honey & Banana/Maize: Chitwan (national poultry capital & Rampur agri-education hub); Kiwi & Rainbow Trout: Dolakha, Nuwakot, Rasuwa."
                    )
                ),
                NoteSection(
                    subheading = "Provincial Priorities",
                    bullets = listOf(
                        "Provincial cold-storage network, pesticide residue rapid-testing at wholesale markets (Kalimati/Balkhu), protected greenhouse farming, and dairy value chains."
                    )
                )
            ),
            quickFact = "Sindhuli and Ramechhap are Nepal's benchmark Junar (Sweet Orange) production zones; Chitwan leads commercial poultry and mustard/maize systems.",
            createdAt = 1010L
        ),

        StudyNoteEntity(
            id = "seed_prov_gandaki",
            title = "Gandaki Province — Agricultural Flagships & Commodity Pockets",
            commissionKey = "GANDAKI",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Provincial Flagships",
            overview = "Gandaki spans from sub-tropical Nawalpur Terai up to trans-Himalayan Mustang and Manang, enabling unmatched niche agro-biodiversity and tourism-linked agribusiness.",
            sections = listOf(
                NoteSection(
                    subheading = "Signature Commodity Pockets & Hubs",
                    bullets = listOf(
                        "High-Altitude Apple & Buckwheat: Mustang (Marpha) and Manang (High Density Apple orchards).",
                        "Mandarin Orange (सुन्तला): Syangja (Citrus Superzone), Gorkha, Tanahun, Myagdi, Parbat.",
                        "Specialty Coffee & Organic Tea: Syangja, Kaski, Lamjung, Baglung; Jethobudho Aromatic Rice: Kaski (Pokhara Valley)."
                    )
                ),
                NoteSection(
                    subheading = "Provincial Priorities",
                    bullets = listOf(
                        "Chief Minister's Environment-Friendly Model Agriculture Village program, citrus rejuvenation against HLB, and agro-ecotourism branding."
                    )
                )
            ),
            quickFact = "Syangja hosts Nepal's flagship Mandarin Citrus Superzone, while Kaski's Jethobudho is Nepal's first locally conserved aromatic rice landrace registered via participatory plant breeding.",
            createdAt = 1011L
        ),

        StudyNoteEntity(
            id = "seed_prov_lumbini",
            title = "Lumbini Province — Agricultural Flagships & Commodity Pockets",
            commissionKey = "LUMBINI",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Provincial Flagships",
            overview = "Lumbini Province blends western Terai rice-wheat-maize plains (Rupandehi, Kapilvastu, Banke, Bardiya, Dang) with renowned hill coffee, ginger, and temperate seed pockets.",
            sections = listOf(
                NoteSection(
                    subheading = "Signature Commodity Pockets & Hubs",
                    bullets = listOf(
                        "Specialty Arabica Coffee: Gulmi & Palpa (pioneer highland coffee pockets).",
                        "Ginger & Turmeric: Palpa, Arghakhanchi, Pyuthan, Rolpa; Maize & Bee-keeping Superzone: Dang.",
                        "Rice, Wheat & Vegetable Seed Production: Rupandehi, Kapilvastu, Bardiya, Banke; Apple & Potato: Eastern Rukum."
                    )
                ),
                NoteSection(
                    subheading = "Provincial Priorities",
                    bullets = listOf(
                        "Smart Agriculture Village program, custom hiring centers for farm mechanization, and commercial seed self-sufficiency."
                    )
                )
            ),
            quickFact = "Dang is a major Maize Superzone and honey hub, while Gulmi and Palpa pioneered Nepal's specialty highland Arabica coffee movement.",
            createdAt = 1012L
        ),

        StudyNoteEntity(
            id = "seed_prov_karnali",
            title = "Karnali Province — Organic Flagship & High-Value Pockets",
            commissionKey = "KARNALI",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Provincial Flagships",
            overview = "Karnali Province officially declared its policy vision as an Organic Agriculture Province, specializing in high-altitude apples, walnuts, indigenous Raithane crops, and vegetable seeds.",
            sections = listOf(
                NoteSection(
                    subheading = "Signature Commodity Pockets & Hubs",
                    bullets = listOf(
                        "Apple Superzone (स्याउ सुपरजोन): Jumla (plus Mugu, Kalikot, Dolpa, Humla) & Jumli Marsi cold-tolerant red rice (world's highest-altitude rice).",
                        "Raithane (Indigenous) Crops: Chino (proso millet), Kaguno (foxtail millet), Phapar (buckwheat), Simi (organic beans), and Uwa (naked barley).",
                        "Vegetable Seed & Ginger/Turmeric: Rukum West (famous radish/onion seed hub), Salyan (ginger & citrus), Dailekh & Surkhet."
                    )
                ),
                NoteSection(
                    subheading = "Provincial Priorities",
                    bullets = listOf(
                        "Organic Karnali Mission ('Mulyaban Logo' certification), bio-pesticide/vermicompost promotion, and walnut/apple nursery expansion."
                    )
                )
            ),
            quickFact = "Jumli Marsi rice cultivated up to ~2,800–3,050 m in Jumla is celebrated as one of the world's highest-altitude rice farming systems.",
            createdAt = 1013L
        ),

        StudyNoteEntity(
            id = "seed_prov_sudurpashchim",
            title = "Sudurpashchim Province — Agricultural Flagships & Pockets",
            commissionKey = "SUDURPASHCHIM",
            postLevelScope = "ALL",
            paper = "Paper I & II",
            tag = "Provincial Flagships",
            overview = "Sudurpashchim unites the fertile far-western Terai breadbasket (Kailali, Kanchanpur) with diverse mid- and high-hill fruit, spice, and off-season vegetable pockets.",
            sections = listOf(
                NoteSection(
                    subheading = "Signature Commodity Pockets & Hubs",
                    bullets = listOf(
                        "Wheat Superzone & Banana/Sugarcane: Kailali & Kanchanpur (also major rice & oilseed hubs).",
                        "Soybean, Ginger & Citrus: Dadeldhura (Potato Superzone), Doti, Baitadi, Achham.",
                        "Walnut, Apple, Olive & Medicinal Herbs: Bajura (famous Bajura olive pocket), Darchula, Bajhang."
                    )
                ),
                NoteSection(
                    subheading = "Provincial Priorities",
                    bullets = listOf(
                        "Chief Minister Integrated Agriculture & Livestock Development Program, wheat seed chains, cold storage in Kailali/Dadeldhura, and hill olive value chains."
                    )
                )
            ),
            quickFact = "Kailali hosts Nepal's landmark Wheat Superzone, while Dadeldhura is renowned for Potato production and Bajura for its unique wild & cultivated Olive belt.",
            createdAt = 1014L
        ),

        // 9A. Subjective Guide for Officers (7th / 8th Level)
        StudyNoteEntity(
            id = "seed_subjective_officer",
            title = "Paper II Subjective Rubric: 10-Mark WTO Impact Essay (Officer)",
            commissionKey = "FEDERAL",
            postLevelScope = "OFFICER",
            paper = "Paper II",
            tag = "Subjective & Practical",
            overview = "High-scoring structured answer framework for 10-mark Officer (7th/8th Level) analytical questions on 'Impact of WTO Membership on Nepalese Agriculture: Opportunities, Challenges & Way Forward'.",
            sections = listOf(
                NoteSection(
                    subheading = "10-Mark Answer Allocation & Structure",
                    bullets = listOf(
                        "1. Crisp Introduction (1.5 Marks): State accession date (April 23, 2004; 147th member), key agreements (Agreement on Agriculture — AoA, SPS, TRIPS), and context of Nepal's smallholder economy.",
                        "2. Opportunities (2.5 Marks): Duty-free/quota-free LDC market access for niche comparative-advantage exports (tea, orthodox coffee, large cardamom, ginger, honey), global SPS alignment, and technology transfer.",
                        "3. Key Challenges (3 Marks): Rising agricultural trade deficit due to high domestic production cost, inability to meet strict SPS/MRL residue standards, subsidized imports from neighboring economies, and weak post-harvest/quarantine infrastructure.",
                        "4. Strategic Way Forward (2 Marks): Strengthen accredited SPS labs, promote Geographical Indication (GI) for niche hill products, utilize WTO Green Box subsidies (research, extension, insurance, infrastructure), and align ADS 2015–2035 commercialization.",
                        "5. Conclusion (1 Mark): Balanced 2-line synthesis tying trade competitiveness with domestic food sovereignty."
                    )
                )
            ),
            quickFact = "Under WTO Agreement on Agriculture (AoA), 'Green Box' supports (research, extension, pest control, crop insurance, infrastructure) have NO spending cap — a vital exam point!",
            createdAt = 1015L
        ),

        // 9B. Practical Guide for JT / JTA (4th / 5th Level)
        StudyNoteEntity(
            id = "seed_practical_jtjta",
            title = "JT / JTA Practical Standards: 1% Bordeaux Mixture & Nursery Beds",
            commissionKey = "ALL",
            postLevelScope = "JT_JTA",
            paper = "Practical",
            tag = "Subjective & Practical",
            overview = "Step-by-step field-level calculation, preparation protocol, and testing of 1% Bordeaux Mixture alongside standard vegetable nursery bed dimensions for JT/JTA written and practical exams.",
            sections = listOf(
                NoteSection(
                    subheading = "Preparation of 1% Bordeaux Mixture (100 Liters)",
                    bullets = listOf(
                        "Formula (1 : 1 : 100): 1 kg Copper Sulphate (CuSO₄·5H₂O / निलो तुथो) + 1 kg Quick Lime (CaO / चुन) + 100 Liters Water.",
                        "Step 1: Dissolve 1 kg Copper Sulphate in 50 L water inside a wooden, plastic, or earthen vessel (NEVER use iron/metal buckets — corrosion ruins efficacy).",
                        "Step 2: Slake 1 kg Quick Lime in another 50 L water container and strain through muslin cloth.",
                        "Step 3: Pour Copper Sulphate solution slowly into Lime solution (or simultaneously into a third non-metallic tank) while stirring constantly — NEVER pour lime into copper sulphate.",
                        "Neutrality Test (Knife / pH Test): Dip a clean iron knife blade for 1–2 minutes. Reddish-brown copper deposit means excess acidic CuSO₄ -> add more lime milk until blade stays clean (target pH ~7.0–7.2). Use freshly prepared mixture the same day."
                    )
                ),
                NoteSection(
                    subheading = "Standard Vegetable Nursery Bed Dimensions",
                    bullets = listOf(
                        "Width: 1.0 m (convenient arm-reach from both sides without trampling soil).",
                        "Length: 3.0 m to 5.0 m (adjustable to field slope & seedling need).",
                        "Height: Raised bed 15–20 cm high during rainy/monsoon season for drainage; sunken/flat bed in dry winter to conserve moisture.",
                        "Drainage Path: 30–50 cm walkway between adjacent beds; Orientation: East–West for uniform sunlight.",
                        "Soil Solarization & Media: Mix well-rotted FYM/compost (3–4 kg/m²), cover moist bed with transparent 25–50 micron polythene for 3–4 weeks in peak summer to suppress damping-off (Pythium) and weeds."
                    )
                )
            ),
            quickFact = "Bordeaux Mixture was discovered by P.M.A. Millardet (1882–1885) in France against Grape Downy Mildew; 1% uses 1 kg CuSO₄ : 1 kg Lime : 100 L Water.",
            createdAt = 1016L
        )
    )
}
