package com.aksharadeeptutor.data.local

import com.aksharadeeptutor.data.model.Chapter
import com.aksharadeeptutor.data.model.Question
import com.aksharadeeptutor.data.model.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseInitializer {
    suspend fun populateIfEmpty(database: AppDatabase) {
        withContext(Dispatchers.IO) {
            val subjectDao = database.subjectDao()
            val chapterDao = database.chapterDao()
            val questionDao = database.questionDao()

            val existingSubjects = subjectDao.getAllSubjects()
            if (existingSubjects.isEmpty()) {
                subjectDao.insertSubjects(listOf(
                    Subject(1, "Science", 0, 0, 15),
                    Subject(2, "Mathematics", 0, 0, 15),
                    Subject(3, "Social Studies", 0, 0, 15)
                ))

                chapterDao.insertChapters(listOf(
                    // Science
                    Chapter(1, 1, "Chemical Reactions and Equations"),
                    Chapter(2, 1, "Acids, Bases and Salts"),
                    Chapter(3, 1, "Metals and Non-metals"),
                    Chapter(4, 1, "Carbon and its Compounds"),
                    Chapter(5, 1, "Periodic Classification of Elements"),
                    Chapter(6, 1, "Life Processes"),
                    Chapter(7, 1, "Control and Coordination"),
                    Chapter(8, 1, "Reproduction in Organisms"),
                    Chapter(9, 1, "Heredity and Evolution"),
                    Chapter(10, 1, "Light - Reflection and Refraction"),
                    Chapter(11, 1, "Human Eye and Colourful World"),
                    Chapter(12, 1, "Electricity"),
                    Chapter(13, 1, "Magnetic Effects of Electric Current"),
                    Chapter(14, 1, "Sources of Energy"),
                    Chapter(15, 1, "Our Environment"),
                    // Mathematics
                    Chapter(16, 2, "Real Numbers"),
                    Chapter(17, 2, "Polynomials"),
                    Chapter(18, 2, "Pair of Linear Equations in Two Variables"),
                    Chapter(19, 2, "Quadratic Equations"),
                    Chapter(20, 2, "Arithmetic Progressions"),
                    Chapter(21, 2, "Triangles"),
                    Chapter(22, 2, "Coordinate Geometry"),
                    Chapter(23, 2, "Introduction to Trigonometry"),
                    Chapter(24, 2, "Applications of Trigonometry"),
                    Chapter(25, 2, "Circles"),
                    Chapter(26, 2, "Constructions"),
                    Chapter(27, 2, "Areas Related to Circles"),
                    Chapter(28, 2, "Surface Areas and Volumes"),
                    Chapter(29, 2, "Statistics"),
                    Chapter(30, 2, "Probability"),
                    // Social Studies
                    Chapter(31, 3, "Resources and Development"),
                    Chapter(32, 3, "Forest and Wildlife Resources"),
                    Chapter(33, 3, "Water Resources"),
                    Chapter(34, 3, "Agriculture"),
                    Chapter(35, 3, "Minerals and Energy Resources"),
                    Chapter(36, 3, "Manufacturing Industries"),
                    Chapter(37, 3, "Lifelines of National Economy"),
                    Chapter(38, 3, "The Rise of Nationalism in Europe"),
                    Chapter(39, 3, "Nationalism in India"),
                    Chapter(40, 3, "Print Culture and the Modern World"),
                    Chapter(41, 3, "The Making of a Global World"),
                    Chapter(42, 3, "Democracy and Diversity"),
                    Chapter(43, 3, "Political Parties"),
                    Chapter(44, 3, "Outcomes of Democracy"),
                    Chapter(45, 3, "Challenges to Democracy")
                ))

                questionDao.insertQuestions(listOf(
                    // Chapter 1 — Chemical Reactions
                    Question(1, 1, "What type of reaction is: 2H2 + O2 -> 2H2O?", "Decomposition", "Combination", "Displacement", "Double Displacement", "Combination", "Two or more reactants combine to form a single product."),
                    Question(2, 1, "Which gas is released when zinc reacts with HCl?", "Oxygen", "Nitrogen", "Hydrogen", "Carbon Dioxide", "Hydrogen", "Zn + 2HCl -> ZnCl2 + H2. Hydrogen gas is evolved."),
                    Question(3, 1, "What is the color of ferrous sulphate crystals?", "Blue", "Green", "White", "Yellow", "Green", "Ferrous sulphate (FeSO4) crystals are green in color."),
                    Question(4, 1, "Rusting of iron is an example of:", "Reduction", "Oxidation", "Sublimation", "Evaporation", "Oxidation", "Rusting involves oxidation of iron in the presence of moisture and oxygen."),
                    Question(5, 1, "Which is a decomposition reaction?", "CaCO3 -> CaO + CO2", "H2 + Cl2 -> 2HCl", "Zn + CuSO4 -> ZnSO4 + Cu", "AgNO3 + NaCl -> AgCl + NaNO3", "CaCO3 -> CaO + CO2", "A single compound breaks down into simpler substances."),
                    // Chapter 2 — Acids Bases Salts
                    Question(6, 2, "What is the pH of a neutral solution?", "0", "7", "14", "1", "7", "A neutral solution has equal H+ and OH- ions, giving pH = 7."),
                    Question(7, 2, "Which acid is present in vinegar?", "Citric Acid", "Acetic Acid", "Hydrochloric Acid", "Sulphuric Acid", "Acetic Acid", "Vinegar contains dilute acetic acid (CH3COOH)."),
                    Question(8, 2, "Bases turn red litmus paper to:", "Blue", "Green", "Yellow", "No change", "Blue", "Bases are alkaline and turn red litmus paper blue."),
                    Question(9, 2, "Chemical formula of baking soda?", "Na2CO3", "NaHCO3", "NaOH", "NaCl", "NaHCO3", "Baking soda is sodium hydrogen carbonate (NaHCO3)."),
                    Question(10, 2, "Which salt is used to soften hard water?", "Washing Soda", "Baking Soda", "Common Salt", "Bleaching Powder", "Washing Soda", "Sodium carbonate (washing soda) removes permanent hardness."),
                    // Chapter 3 — Metals and Non-metals
                    Question(11, 3, "Which metal is liquid at room temperature?", "Iron", "Mercury", "Aluminum", "Copper", "Mercury", "Mercury (Hg) is the only metal liquid at room temperature."),
                    Question(12, 3, "Which non-metal conducts electricity?", "Sulphur", "Carbon (Graphite)", "Phosphorus", "Nitrogen", "Carbon (Graphite)", "Graphite conducts electricity due to free electrons."),
                    Question(13, 3, "Most abundant metal in Earth's crust?", "Iron", "Copper", "Aluminum", "Gold", "Aluminum", "Aluminum makes up about 8% of Earth's crust."),
                    Question(14, 3, "Which metal reacts vigorously with cold water?", "Iron", "Copper", "Sodium", "Silver", "Sodium", "Sodium reacts violently with cold water releasing hydrogen gas."),
                    Question(15, 3, "Galvanization is coating iron with:", "Copper", "Zinc", "Tin", "Aluminum", "Zinc", "Zinc coating prevents iron from rusting."),
                    // Chapter 4 — Carbon and its Compounds
                    Question(16, 4, "The property of carbon to form long chains is called:", "Isomerism", "Catenation", "Hybridization", "Polymerization", "Catenation", "Catenation is the self-linking property of carbon atoms."),
                    Question(17, 4, "Which of these is a saturated hydrocarbon?", "Ethene", "Ethyne", "Ethane", "Benzene", "Ethane", "Ethane (C2H6) has only single bonds — it is saturated."),
                    Question(18, 4, "Functional group of alcohols is:", "-COOH", "-CHO", "-OH", "-NH2", "-OH", "Alcohols contain the hydroxyl (-OH) functional group."),
                    Question(19, 4, "Soap is made by the process of:", "Esterification", "Saponification", "Hydrogenation", "Fermentation", "Saponification", "Saponification is the reaction of fat with alkali to form soap."),
                    Question(20, 4, "The simplest hydrocarbon is:", "Ethane", "Propane", "Methane", "Butane", "Methane", "Methane (CH4) is the simplest alkane."),
                    // Chapter 6 — Life Processes
                    Question(21, 6, "Which organ pumps blood in the human body?", "Liver", "Kidney", "Heart", "Lungs", "Heart", "The heart pumps blood throughout the body."),
                    Question(22, 6, "Functional unit of the kidney is:", "Neuron", "Nephron", "Alveoli", "Villi", "Nephron", "Nephrons filter blood and produce urine."),
                    Question(23, 6, "Which enzyme is present in saliva?", "Pepsin", "Trypsin", "Amylase", "Lipase", "Amylase", "Salivary amylase breaks down starch."),
                    Question(24, 6, "Breakdown of glucose to pyruvate occurs in:", "Mitochondria", "Cytoplasm", "Nucleus", "Ribosome", "Cytoplasm", "Glycolysis occurs in the cytoplasm."),
                    Question(25, 6, "Blood vessels that carry blood away from heart:", "Veins", "Arteries", "Capillaries", "Venules", "Arteries", "Arteries carry oxygenated blood from the heart."),
                    // Chapter 7 — Control and Coordination
                    Question(26, 7, "The basic unit of the nervous system is:", "Nephron", "Neuron", "Hormone", "Synapse", "Neuron", "Neurons are the structural and functional units of the nervous system."),
                    Question(27, 7, "Which gland is called the master gland?", "Thyroid", "Adrenal", "Pituitary", "Pancreas", "Pituitary", "The pituitary gland controls other endocrine glands."),
                    Question(28, 7, "Insulin is produced by:", "Liver", "Kidney", "Pancreas", "Thyroid", "Pancreas", "Beta cells of the pancreas produce insulin."),
                    Question(29, 7, "Reflex actions are controlled by:", "Brain", "Spinal Cord", "Hormones", "Eyes", "Spinal Cord", "Reflex arcs pass through the spinal cord without involving the brain."),
                    Question(30, 7, "Plant hormone responsible for growth is:", "Auxin", "Cytokinin", "Gibberellin", "Auxin", "Auxin", "Auxin promotes cell elongation and plant growth."),
                    // Chapter 16 — Real Numbers
                    Question(31, 16, "Every composite number is a product of:", "Prime numbers", "Even numbers", "Odd numbers", "Rational numbers", "Prime numbers", "Fundamental Theorem of Arithmetic states this."),
                    Question(32, 16, "HCF of 12 and 18 is:", "2", "3", "6", "12", "6", "6 is the largest number dividing both 12 and 18."),
                    Question(33, 16, "Which is an irrational number?", "sqrt(4)", "sqrt(9)", "sqrt(2)", "22/7", "sqrt(2)", "sqrt(2) cannot be expressed as p/q."),
                    Question(34, 16, "Decimal expansion of 1/7 is:", "Terminating", "Non-terminating repeating", "Non-terminating non-repeating", "None", "Non-terminating repeating", "1/7 = 0.142857142857..."),
                    Question(35, 16, "If LCM(a,b)=36 and HCF(a,b)=6, then a×b=?", "36", "216", "18", "42", "216", "LCM × HCF = Product of numbers = 36 × 6 = 216."),
                    // Chapter 17 — Polynomials
                    Question(36, 17, "A polynomial of degree 2 is called:", "Linear", "Quadratic", "Cubic", "Bi-quadratic", "Quadratic", "Quadratic polynomials have highest power 2."),
                    Question(37, 17, "Zeroes of x² - 5x + 6 are:", "2, 3", "1, 6", "-2, -3", "-1, -6", "2, 3", "Factoring: (x-2)(x-3) = 0."),
                    Question(38, 17, "Sum of zeroes of 2x² + 3x - 5 is:", "-3/2", "3/2", "-5/2", "5/2", "-3/2", "Sum of zeroes = -b/a = -3/2."),
                    Question(39, 17, "If one zero of x² - 4x + k is 2, then k = ?", "2", "4", "6", "8", "4", "Substituting x=2: 4 - 8 + k = 0, k = 4."),
                    Question(40, 17, "Degree of a zero polynomial is:", "0", "1", "Undefined", "-1", "Undefined", "The degree of zero polynomial is not defined."),
                    // Chapter 19 — Quadratic Equations
                    Question(41, 19, "Standard form of a quadratic equation is:", "ax + b = 0", "ax² + bx + c = 0", "ax³ + b = 0", "ax² + b = 0", "ax² + bx + c = 0", "A quadratic equation has degree 2."),
                    Question(42, 19, "Discriminant of ax² + bx + c = 0 is:", "b² - 4ac", "b² + 4ac", "4ac - b²", "b - 4ac", "b² - 4ac", "D = b² - 4ac determines the nature of roots."),
                    Question(43, 19, "If D = 0, the roots are:", "Real and distinct", "Real and equal", "Not real", "Imaginary", "Real and equal", "D = 0 means both roots are equal."),
                    Question(44, 19, "Roots of x² - 5x + 6 = 0 are:", "2 and 3", "1 and 6", "-2 and -3", "3 and 5", "2 and 3", "(x-2)(x-3) = 0 gives x = 2 or 3."),
                    Question(45, 19, "Method to find roots by completing square uses:", "Factorization", "Adding (b/2a)²", "Vieta's formulas", "Graphing", "Adding (b/2a)²", "Completing the square adds the square of half the coefficient."),
                    // Chapter 21 — Triangles
                    Question(46, 21, "In similar triangles, corresponding sides are:", "Equal", "Proportional", "Perpendicular", "Parallel", "Proportional", "Similar triangles have proportional corresponding sides."),
                    Question(47, 21, "Ratio of areas of similar triangles equals square of ratio of:", "Altitudes", "Corresponding sides", "Medians", "All of these", "Corresponding sides", "Area ratio = (side ratio)²."),
                    Question(48, 21, "Pythagoras theorem applies to:", "Any triangle", "Right-angled triangle", "Isosceles triangle", "Equilateral triangle", "Right-angled triangle", "a² + b² = c² holds for right triangles only."),
                    Question(49, 21, "If ABC ~ DEF and AB/DE = 2/3, then ar(ABC)/ar(DEF) =?", "2/3", "4/9", "3/2", "9/4", "4/9", "Ratio of areas = (2/3)² = 4/9."),
                    Question(50, 21, "Basic Proportionality Theorem is known as:", "Pythagoras Theorem", "Thales Theorem", "Mid-point Theorem", "Heron's Theorem", "Thales Theorem", "Thales stated a line parallel to one side divides others proportionally."),
                    // Chapter 23 — Trigonometry
                    Question(51, 23, "Value of sin 30°:", "1", "1/2", "sqrt(3)/2", "0", "1/2", "sin 30° = 1/2 is a standard value."),
                    Question(52, 23, "If sin θ = 3/5, then cos θ =?", "4/5", "3/4", "5/3", "5/4", "4/5", "cos θ = sqrt(1 - 9/25) = 4/5."),
                    Question(53, 23, "tan 45° equals:", "0", "1", "sqrt(3)", "1/sqrt(3)", "1", "tan 45° = sin 45°/cos 45° = 1."),
                    Question(54, 23, "Which identity is correct?", "sin²+cos²=1", "sin²-cos²=1", "tan²+sec²=1", "cot²-cosec²=1", "sin²+cos²=1", "This is the fundamental Pythagorean identity."),
                    Question(55, 23, "Value of cos 0°:", "0", "1", "-1", "1/2", "1", "cos 0° = 1 as adjacent equals hypotenuse at 0°."),
                    // Chapter 31 — Resources and Development
                    Question(56, 31, "Which is a renewable resource?", "Coal", "Petroleum", "Solar Energy", "Natural Gas", "Solar Energy", "Solar energy is naturally replenished and inexhaustible."),
                    Question(57, 31, "Laterite soil is found mainly in:", "Northern Plains", "Deccan Plateau", "Coastal Areas", "Deserts", "Coastal Areas", "Laterite soil develops in high rainfall and temperature areas."),
                    Question(58, 31, "Which prevents soil erosion on hill slopes?", "Deforestation", "Overgrazing", "Terrace Farming", "Mining", "Terrace Farming", "Terraces cut into slopes to hold water and soil."),
                    Question(59, 31, "Iron ore is which type of resource?", "Renewable", "Non-renewable", "Flow", "Biotic", "Non-renewable", "Iron ore is a mineral that cannot be replenished quickly."),
                    Question(60, 31, "Land degradation is caused by:", "Afforestation", "Over-irrigation", "Crop rotation", "Shelter belts", "Over-irrigation", "Excessive irrigation leads to waterlogging and salinity."),
                    // Chapter 38 — Rise of Nationalism in Europe
                    Question(61, 38, "The French Revolution began in:", "1776", "1789", "1815", "1848", "1789", "The French Revolution started in 1789 with storming of Bastille."),
                    Question(62, 38, "Who led the Jacobin Club?", "Louis XVI", "Robespierre", "Napoleon", "Voltaire", "Robespierre", "Maximilien Robespierre led the radical Jacobin faction."),
                    Question(63, 38, "Treaty of Vienna was signed in:", "1815", "1789", "1848", "1919", "1815", "The Congress of Vienna redrew European borders after Napoleon."),
                    Question(64, 38, "'When France sneezes...' was said by:", "Mazzini", "Metternich", "Garibaldi", "Bismarck", "Metternich", "Duke Metternich recognized French revolutionary influence."),
                    Question(65, 38, "Italy was unified in:", "1861", "1871", "1848", "1815", "1861", "Italy was unified in 1861 under Victor Emmanuel II."),
                    // Chapter 39 — Nationalism in India
                    Question(66, 39, "Non-Cooperation Movement was launched in:", "1920", "1930", "1942", "1919", "1920", "Gandhi launched Non-Cooperation Movement in 1920."),
                    Question(67, 39, "Rowlatt Act was passed in:", "1919", "1920", "1905", "1930", "1919", "The Rowlatt Act (1919) allowed detention without trial."),
                    Question(68, 39, "Jallianwala Bagh massacre occurred in:", "1920", "1919", "1915", "1930", "1919", "The massacre occurred on 13 April 1919 in Amritsar."),
                    Question(69, 39, "Salt March began from:", "Delhi", "Calcutta", "Sabarmati Ashram", "Bombay", "Sabarmati Ashram", "Gandhi started the Dandi March from Sabarmati Ashram in 1930."),
                    Question(70, 39, "Quit India Movement was launched in:", "1942", "1930", "1920", "1940", "1942", "Gandhi launched the Quit India Movement on 8 August 1942."),
                    // Chapter 20 — Arithmetic Progressions
                    Question(71, 20, "In an AP, the common difference is:", "a2 - a1", "a1 - a2", "a1 × a2", "a2 / a1", "a2 - a1", "d = a2 - a1 is the common difference."),
                    Question(72, 20, "nth term of AP: an = a + (n-1)d. If a=2, d=3, n=5, then a5=?", "14", "15", "16", "17", "14", "a5 = 2 + (5-1)×3 = 2 + 12 = 14."),
                    Question(73, 20, "Sum of first n natural numbers is:", "n(n+1)", "n(n+1)/2", "n²", "n(n-1)/2", "n(n+1)/2", "S = n(n+1)/2 is the formula for sum of first n natural numbers."),
                    Question(74, 20, "If AP has a=1, d=2, how many terms to reach 19?", "9", "10", "8", "11", "10", "19 = 1 + (n-1)×2, so n-1 = 9, n = 10."),
                    Question(75, 20, "Which of these is an AP?", "1,2,4,8", "1,3,6,10", "2,4,6,8", "1,4,9,16", "2,4,6,8", "The difference between consecutive terms is constant (2)."),
                    // Chapter 22 — Coordinate Geometry
                    Question(76, 22, "Distance between (0,0) and (3,4) is:", "5", "7", "6", "4", "5", "Distance = sqrt(3²+4²) = sqrt(9+16) = sqrt(25) = 5."),
                    Question(77, 22, "Midpoint of (2,4) and (6,8) is:", "(4,6)", "(3,5)", "(8,12)", "(2,4)", "(4,6)", "Midpoint = ((2+6)/2, (4+8)/2) = (4,6)."),
                    Question(78, 22, "Slope of x-axis is:", "1", "0", "Undefined", "-1", "0", "The x-axis is horizontal, so its slope is 0."),
                    Question(79, 22, "Section formula divides line segment:", "Internally only", "Externally only", "Both internally and externally", "Neither", "Both internally and externally", "Section formula works for both internal and external division."),
                    Question(80, 22, "Area of triangle with vertices (0,0),(4,0),(0,3) is:", "6", "12", "7", "5", "6", "Area = (1/2) × base × height = (1/2) × 4 × 3 = 6."),
                    // Chapter 32 — Forest and Wildlife Resources
                    Question(81, 32, "Project Tiger was launched in:", "1973", "1980", "1985", "1990", "1973", "Project Tiger was launched in 1973 to protect tigers."),
                    Question(82, 32, "IUCN stands for:", "International Union for Conservation of Nature", "Indian Union for Conservation Networks", "International Unit for Carbon Neutrality", "Indian Union for Climate Negotiations", "International Union for Conservation of Nature", "IUCN maintains the Red List of threatened species."),
                    Question(83, 32, "Which is a biosphere reserve in India?", "Kaziranga", "Nanda Devi", "Corbett", "Bandipur", "Nanda Devi", "Nanda Devi is one of India's 18 biosphere reserves."),
                    Question(84, 32, "Chipko Movement was associated with:", "Water conservation", "Forest conservation", "Soil conservation", "Wildlife conservation", "Forest conservation", "The Chipko Movement (1970s) protected Himalayan forests by hugging trees."),
                    Question(85, 32, "Which species is critically endangered in India?", "Asiatic Lion", "One-horned Rhinoceros", "Bengal Tiger", "Snow Leopard", "Asiatic Lion", "The Asiatic Lion is critically endangered, found only in Gir Forest."),
                    // Chapter 29 — Statistics
                    Question(86, 29, "The mean of 2, 4, 6, 8, 10 is:", "5", "6", "7", "8", "6", "Mean = (2+4+6+8+10)/5 = 30/5 = 6."),
                    Question(87, 29, "Mode is:", "Middle value", "Most frequent value", "Average value", "Sum of values", "Most frequent value", "Mode is the value that appears most often."),
                    Question(88, 29, "Median of 3, 5, 7, 9, 11 is:", "7", "5", "9", "6", "7", "The middle value of the ordered set 3,5,7,9,11 is 7."),
                    Question(89, 29, "Empirical relation: Mode =", "Mean - 2 Median", "3 Median - 2 Mean", "2 Mean - 3 Median", "Median - 2 Mean", "3 Median - 2 Mean", "Empirical relation: Mode = 3 Median - 2 Mean."),
                    Question(90, 29, "Ogive is used to find:", "Mean", "Mode", "Median", "Range", "Median", "The point where less-than and more-than ogives intersect gives the median."),
                    // Chapter 33 — Water Resources
                    Question(91, 33, "Sardar Sarovar Dam is on river:", "Narmada", "Godavari", "Krishna", "Mahanadi", "Narmada", "Sardar Sarovar is India's largest dam on the Narmada river."),
                    Question(92, 33, "Rainwater harvesting is used for:", "Increasing rainfall", "Storing rainwater", "Purifying water", "Generating electricity", "Storing rainwater", "Rainwater harvesting collects and stores rain for later use."),
                    Question(93, 33, "Which method is most efficient for irrigation?", "Flood irrigation", "Drip irrigation", "Sprinkler irrigation", "Canal irrigation", "Drip irrigation", "Drip irrigation delivers water directly to roots, minimizing waste."),
                    Question(94, 33, "Bhakra Nangal Dam is on river:", "Sutlej", "Beas", "Ravi", "Chenab", "Sutlej", "Bhakra Nangal Dam is built across the Sutlej river in Himachal Pradesh."),
                    Question(95, 33, "Water scarcity is mainly due to:", "Low rainfall only", "Overexploitation and mismanagement", "Too many rivers", "Excess groundwater", "Overexploitation and mismanagement", "Overuse and mismanagement of water resources cause scarcity."),
                    // Chapter 42 — Democracy and Diversity
                    Question(96, 42, "Democracy is a form of government where:", "One person rules", "Military rules", "People rule", "Rich rule", "People rule", "Democracy is government of, by, and for the people."),
                    Question(97, 42, "Social division is based on:", "Class only", "Caste only", "Race only", "All of these", "All of these", "Social divisions can be based on caste, class, race, religion, etc."),
                    Question(98, 42, "Which country had apartheid system?", "India", "USA", "South Africa", "UK", "South Africa", "Apartheid was racial segregation enforced by the South African government."),
                    Question(99, 42, "Overlapping social differences lead to:", "Harmony", "Violent conflict", "Unity", "Economic growth", "Violent conflict", "When multiple social divisions coincide, they intensify into conflict."),
                    Question(100, 42, "Cross-cutting differences tend to:", "Divide society", "Overlap conflicts", "Reduce conflicts", "Create inequality", "Reduce conflicts", "Cross-cutting divisions make extreme polarization less likely.")
                ))
            }
        }
    }
}
