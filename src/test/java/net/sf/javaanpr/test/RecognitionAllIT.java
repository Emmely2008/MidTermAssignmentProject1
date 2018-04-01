package net.sf.javaanpr.test;


import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import net.sf.javaanpr.test.util.CSVUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.rules.ErrorCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Arrays;
import java.util.Properties;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


import static org.junit.Assert.*;
class RecognitionAllIT {


    private static final int currentlyCorrectSnapshots = 53;
    private static final Logger logger = LoggerFactory.getLogger(RecognitionIT.class);
    @Rule
    public ErrorCollector recognitionErrors = new ErrorCollector();

    //    TODO 3 Fix for some strange encodings of jpeg images - they don't always load correctly
    //    See: http://stackoverflow.com/questions/2408613/problem-reading-jpeg-image-using-imageio-readfile-file
    //    B/W images load without a problem: for now - using snapshots/test_041.jpg
    @Test
    public void intelligenceSingleTest() throws IOException, ParserConfigurationException, SAXException {
        logger.info("###### MY RUNNING: NAME_OF_TEST ######");
        final String image = "snapshots/test_041.jpg";
        CarSnapshot carSnap = new CarSnapshot(image);
        assertNotNull("carSnap is null", carSnap);
        assertNotNull("carSnap.image is null", carSnap.getImage());
        Intelligence intel = new Intelligence();
        assertNotNull(intel);
        String spz = intel.recognize(carSnap);
        assertNotNull("The licence plate is null - are you sure the image has the correct color space?", spz);
        assertEquals("LM025BD", spz);
        carSnap.close();
    }

    /**
     * Goes through all the test images and checks if they are correctly recognized.
     * <p>
     * This is only an information test right now, doesn't fail.
     *
     * @throws Exception an Exception
     */
    @Before
    public void testAllSnapshots() throws Exception {
        logger.info("###### RUNNING: NAME_OF_TEST ######");
        String snapshotDirPath = "src/test/resources/snapshots";
        String resultsPath = "src/test/resources/results.properties";
        InputStream resultsStream = new FileInputStream(new File(resultsPath));

        Properties properties = new Properties();
        properties.load(resultsStream);
        resultsStream.close();
       // assertTrue(properties.size() > 0);

        File snapshotDir = new File(snapshotDirPath);
        File[] snapshots = snapshotDir.listFiles();
       // assertNotNull(snapshots);
       // assertTrue(snapshots.length > 0);

        Intelligence intel = new Intelligence();
        //assertNotNull(intel);

        int correctCount = 0;
        int counter = 0;
        boolean correct;
        // TODO declare filename with todays date and also remove previous file
        String csvFile = "/generated_data.csv";
        FileWriter writer = new FileWriter(csvFile);
        for (File snap : snapshots) {
            // TODO input right type of data ibnto the csv file

            correct = false;
            CarSnapshot carSnap = new CarSnapshot(new FileInputStream(snap));
            //assertNotNull("carSnap is null", carSnap);
            //assertNotNull("carSnap.image is null", carSnap.getImage());

            String snapName = snap.getName();
            String plateCorrect = properties.getProperty(snapName);
            //assertNotNull(plateCorrect);

            String numberPlate = intel.recognize(carSnap, false);
            System.out.println("Write to file");
            // Write down to file.
            // TODO enable these checks once the test passes
            // Are you sure the image has the correct color space?
            // recognitionErrors.checkThat("The licence plate is null", numberPlate, is(notNullValue()));
            // recognitionErrors.checkThat("The file \"" + snapName + "\" was incorrectly recognized.", numberPlate,
            // is(plateCorrect));

            if (numberPlate != null && numberPlate.equals(plateCorrect)) {
                CSVUtils.writeLine(writer, Arrays.asList(snapName, plateCorrect,numberPlate, "true"));

                correctCount++;
                correct = true;
            }
            else{
                if(numberPlate == null){
                    numberPlate = "null";
                }
                CSVUtils.writeLine(writer, Arrays.asList(snapName, plateCorrect, numberPlate, "false"));

            }
            carSnap.close();
            counter++;
           // logger.debug("Finished recognizing {} ({} of {})\t{}", snapName, counter, snapshots.length,
             //       correct ? "correct" : "incorrect");
        }


        writer.flush();
        writer.close();

        //logger.info("Correct images: {}, total images: {},qxz accuracy: {}%", correctCount, snapshots.length,
          //      (float) correctCount / (float) snapshots.length * 100f);
        //assertEquals(currentlyCorrectSnapshots, correctCount);
    }


    // TODO paramiterized test
/*
    @ParameterizedTest(name = "{index} => calculate({0}) should return {1}")
    @CsvFileSource(resources = {"/Users/eacl/IdeaProjects/javaanpr/abc.csv"}, delimiter = ',')
    void fizzBuzzCsv(String number, String expectedResult,String number3,String number5) {
        assertEquals(number,expectedResult);
    }*/

    @DisplayName("Should check is licensplates are equal to testresults")
    @ParameterizedTest(name = "{index} => image={0} extpected={1}")
    @CsvFileSource(resources = "/results.csv", delimiter = '=')
    void checkSigns(String image_path, String expectedResult) throws Exception {

        final String image = "snapshots/"+ image_path;
        CarSnapshot carSnap = new CarSnapshot(image);
        assertNotNull("carSnap is null", carSnap);
        assertNotNull("carSnap.image is null", carSnap.getImage());
        Intelligence intel = new Intelligence();
        assertNotNull(intel);
        String spz = intel.recognize(carSnap);
        //assertNotNull("The licence plate is null - are you sure the image has the correct color space?", spz);
        //assertEquals(expectedResult, spz);
        assertThat(expectedResult, is(equalTo(spz)));

        carSnap.close();

    }
    @DisplayName("Checks if any resulting lisence plates are null")
    @ParameterizedTest(name = "{index} => image={0} extpected={1}")
    @CsvFileSource(resources = "/results.csv", delimiter = '=')
    void checkSignsForNulls(String image_path, String expectedResult) throws Exception {

        final String image = "snapshots/"+ image_path;
        CarSnapshot carSnap = new CarSnapshot(image);
        assertNotNull("carSnap is null", carSnap);
        assertNotNull("carSnap.image is null", carSnap.getImage());
        Intelligence intel = new Intelligence();
        assertNotNull(intel);
        String spz = intel.recognize(carSnap);
        //assertNotNull("The licence plate is null - are you sure the image has the correct color space?", spz);
        //assertEquals(expectedResult, spz);
        assertThat(spz, is(notNullValue()));

        carSnap.close();

    }
}
