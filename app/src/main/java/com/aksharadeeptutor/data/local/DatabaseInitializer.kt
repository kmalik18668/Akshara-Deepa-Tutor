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
                val subjects = listOf(
                    Subject(1, "Science", 0, 0),
                    Subject(2, "Mathematics", 0, 0),
                    Subject(3, "Social Studies", 0, 0)
                )
                subjectDao.insertSubjects(subjects)

                val chapters = listOf(
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
                )
                chapterDao.insertChapters(chapters)

                val questions = listOf(
                    Question(1, 1, "What type of reaction is: 2H2 + O2 -> 2H2O?", "Decomposition", "Combination", "Displacement", "Double Displacement", "Combination", "Two or more reactants combine to form a single product."),
                    Question(2, 1, "Which gas is released during the reaction of zinc with hydrochloric acid?", "Oxygen", "Nitrogen", "Hydrogen", "Carbon Dioxide", "Hydrogen", "Zn + 2HCl -> ZnCl2 + H2. Hydrogen gas is evolved."),
                    Question(3, 1, "What is the color of ferrous sulphate crystals?", "Blue", "Green", "White", "Yellow", "Green", "Ferrous sulphate (FeSO4) crystals are green in color."),
                    Question(4, 1, "Rusting of iron is an example of:", "Reduction", "Oxidation", "Sublimation", "Evaporation", "Oxidation", "Rusting involves oxidation of iron in the presence of moisture and oxygen."),
                    Question(5, 1, "Which of the following is a decomposition reaction?", "CaCO3 -> CaO + CO2", "H2 + Cl2 -> 2HCl", "Zn + CuSO4 -> ZnSO4 + Cu", "AgNO3 + NaCl -> AgCl + NaNO3", "CaCO3 -> CaO + CO2", "A single compound breaks down into two or more simpler substances."),

                    Question(6, 2, "What is the pH of a neutral solution?", "0", "7", "14", "1", "7", "A neutral solution has equal concentrations of H+ and OH- ions, giving pH = 7."),
                    Question(7, 2, "Which acid is present in vinegar?", "Citric Acid", "Acetic Acid", "Hydrochloric Acid", "Sulphuric Acid", "Acetic Acid", "Vinegar contains dilute acetic acid (CH3COOH)."),
                    Question(8, 2, "Bases turn red litmus paper to which color?", "Blue", "Green", "Yellow", "No change", "Blue", "Bases are alkaline and turn red litmus paper blue."),
                    Question(9, 2, "What is the chemical formula of baking soda?", "Na2CO3", "NaHCO3", "NaOH", "NaCl", "NaHCO3", "Baking soda is sodium hydrogen carbonate (NaHCO3)."),
                    Question(10, 2, "Which salt is used for softening hard water?", "Washing Soda", "Baking Soda", "Common Salt", "Bleaching Powder", "Washing Soda", "Sodium carbonate (washing soda) removes permanent hardness of water."),

                    Question(11, 3, "Which metal is liquid at room temperature?", "Iron", "Mercury", "Aluminum", "Copper", "Mercury", "Mercury (Hg) is the only metal that is liquid at room temperature."),
                    Question(12, 3, "Which non-metal is a good conductor of electricity?", "Sulphur", "Carbon (Graphite)", "Phosphorus", "Nitrogen", "Carbon (Graphite)", "Graphite, an allotrope of carbon, conducts electricity due to free electrons."),
                    Question(13, 3, "What is the most abundant metal in Earth's crust?", "Iron", "Copper", "Aluminum", "Gold", "Aluminum", "Aluminum makes up about 8% of Earth's crust by weight."),
                    Question(14, 3, "Which metal reacts vigorously with cold water?", "Iron", "Copper", "Sodium", "Silver", "Sodium", "Sodium reacts violently with cold water producing hydrogen gas and heat."),
                    Question(15, 3, "Galvanization is the process of coating iron with:", "Copper", "Zinc", "Tin", "Aluminum", "Zinc", "Zinc coating prevents iron from rusting by acting as a sacrificial layer."),

                    Question(16, 6, "Which organ is responsible for pumping blood in the human body?", "Liver", "Kidney", "Heart", "Lungs", "Heart", "The heart is a muscular organ that pumps blood throughout the body."),
                    Question(17, 6, "What is the functional unit of the kidney?", "Neuron", "Nephron", "Alveoli", "Villi", "Nephron", "Nephrons filter blood and produce urine in the kidneys."),
                    Question(18, 6, "Which enzyme is present in saliva?", "Pepsin", "Trypsin", "Amylase", "Lipase", "Amylase", "Salivary amylase breaks down starch into simpler sugars."),
                    Question(19, 6, "The process of breakdown of glucose to pyruvate occurs in:", "Mitochondria", "Cytoplasm", "Nucleus", "Ribosome", "Cytoplasm", "Glycolysis occurs in the cytoplasm of cells."),
                    Question(20, 6, "Which blood vessels carry blood away from the heart?", "Veins", "Arteries", "Capillaries", "Venules", "Arteries", "Arteries carry oxygenated blood away from the heart to body tissues."),

                    Question(21, 16, "Every composite number can be expressed as a product of:", "Prime numbers", "Even numbers", "Odd numbers", "Rational numbers", "Prime numbers", "Fundamental Theorem of Arithmetic states this uniquely."),
                    Question(22, 16, "The HCF of 12 and 18 is:", "2", "3", "6", "12", "6", "HCF(12, 18) = 6 as 6 is the largest number dividing both."),
                    Question(23, 16, "Which of the following is an irrational number?", "sqrt(4)", "sqrt(9)", "sqrt(2)", "22/7", "sqrt(2)", "sqrt(2) cannot be expressed as a ratio of two integers."),
                    Question(24, 16, "The decimal expansion of 1/7 is:", "Terminating", "Non-terminating repeating", "Non-terminating non-repeating", "None", "Non-terminating repeating", "1/7 = 0.142857142857... which is non-terminating repeating."),
                    Question(25, 16, "If LCM(a, b) = 36 and HCF(a, b) = 6, then a x b = ?", "36", "216", "18", "42", "216", "LCM x HCF = Product of numbers, so 36 x 6 = 216."),

                    Question(26, 17, "A polynomial of degree 2 is called:", "Linear", "Quadratic", "Cubic", "Bi-quadratic", "Quadratic", "Quadratic polynomials have the highest power of variable as 2."),
                    Question(27, 17, "The zeroes of x^2 - 5x + 6 are:", "2, 3", "1, 6", "-2, -3", "-1, -6", "2, 3", "Factoring: (x-2)(x-3) = 0, so x = 2 or x = 3."),
                    Question(28, 17, "Sum of zeroes of 2x^2 + 3x - 5 is:", "-3/2", "3/2", "-5/2", "5/2", "-3/2", "Sum of zeroes = -b/a = -3/2."),
                    Question(29, 17, "If one zero of x^2 - 4x + k is 2, then k = ?", "2", "4", "6", "8", "4", "Substituting x=2: 4 - 8 + k = 0, so k = 4."),
                    Question(30, 17, "The degree of a zero polynomial is:", "0", "1", "Undefined", "-1", "Undefined", "The degree of zero polynomial is not defined."),

                    Question(31, 21, "If two triangles are similar, their corresponding sides are:", "Equal", "Proportional", "Perpendicular", "Parallel", "Proportional", "Similar triangles have proportional corresponding sides."),
                    Question(32, 21, "In similar triangles, the ratio of areas equals the square of the ratio of:", "Altitudes", "Corresponding sides", "Medians", "All of these", "Corresponding sides", "Area ratio = (side ratio)^2 for similar triangles."),
                    Question(33, 21, "Pythagoras theorem is applicable in:", "Any triangle", "Right-angled triangle", "Isosceles triangle", "Equilateral triangle", "Right-angled triangle", "a^2 + b^2 = c^2 holds only for right-angled triangles."),
                    Question(34, 21, "If triangle ABC ~ triangle DEF and AB/DE = 2/3, then ar(ABC)/ar(DEF) = ?", "2/3", "4/9", "3/2", "9/4", "4/9", "Ratio of areas = (ratio of sides)^2 = (2/3)^2 = 4/9."),
                    Question(35, 21, "Basic Proportionality Theorem is also known as:", "Pythagoras Theorem", "Thales Theorem", "Mid-point Theorem", "Heron's Theorem", "Thales Theorem", "Thales stated that a line parallel to one side divides other sides proportionally."),

                    Question(36, 31, "Which of the following is a renewable resource?", "Coal", "Petroleum", "Solar Energy", "Natural Gas", "Solar Energy", "Solar energy is replenished naturally and is inexhaustible."),
                    Question(37, 31, "Laterite soil is found mainly in:", "Northern Plains", "Deccan Plateau", "Coastal Areas", "Deserts", "Coastal Areas", "Laterite soil develops in areas with high rainfall and temperature."),
                    Question(38, 31, "Soil conservation methods include:", "Deforestation", "Overgrazing", "Terrace Farming", "Mining", "Terrace Farming", "Terrace farming prevents soil erosion on hill slopes."),
                    Question(39, 31, "Which type of resource is iron ore?", "Renewable", "Non-renewable", "Flow", "Biotic", "Non-renewable", "Iron ore is a mineral resource that cannot be replenished quickly."),
                    Question(40, 31, "Land degradation is caused by:", "Afforestation", "Over-irrigation", "Crop rotation", "Shelter belts", "Over-irrigation", "Excessive irrigation leads to waterlogging and salinity."),

                    Question(41, 38, "The French Revolution began in which year?", "1776", "1789", "1815", "1848", "1789", "The French Revolution started in 1789 with the storming of Bastille."),
                    Question(42, 38, "Who was the leader of the Jacobin Club?", "Louis XVI", "Robespierre", "Napoleon", "Voltaire", "Robespierre", "Maximilien Robespierre led the radical Jacobin faction."),
                    Question(43, 38, "The Treaty of Vienna was signed in:", "1815", "1789", "1848", "1919", "1815", "The Congress of Vienna redrew European borders after Napoleon's defeat."),
                    Question(44, 38, "Who said 'When France sneezes, the rest of Europe catches cold'?", "Mazzini", "Metternich", "Garibaldi", "Bismarck", "Metternich", "Duke Metternich recognized the influence of French revolutionary ideas."),
                    Question(45, 38, "Unification of Italy was completed in:", "1861", "1871", "1848", "1815", "1861", "Italy was unified in 1861 under Victor Emmanuel II."),

                    Question(46, 23, "The value of sin 30 degrees is:", "1", "1/2", "sqrt(3)/2", "0", "1/2", "sin 30 degrees = 1/2 is a standard trigonometric value."),
                    Question(47, 23, "If sin theta = 3/5, then cos theta = ?", "4/5", "3/4", "5/3", "5/4", "4/5", "Using sin^2 + cos^2 = 1, cos theta = sqrt(1 - 9/25) = 4/5."),
                    Question(48, 23, "tan 45 degrees equals:", "0", "1", "sqrt(3)", "1/sqrt(3)", "1", "tan 45 degrees = sin 45/cos 45 = 1."),
                    Question(49, 23, "Which identity is correct?", "sin^2 + cos^2 = 1", "sin^2 - cos^2 = 1", "tan^2 + sec^2 = 1", "cot^2 - cosec^2 = 1", "sin^2 + cos^2 = 1", "This is the fundamental Pythagorean trigonometric identity."),
                    Question(50, 23, "The value of cos 0 degrees is:", "0", "1", "-1", "1/2", "1", "cos 0 degrees = 1 as the adjacent side equals the hypotenuse at 0 degrees.")
                )
                questionDao.insertQuestions(questions)
            }
        }
    }
}
