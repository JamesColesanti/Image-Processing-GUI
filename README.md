IMAGE PROCESSING APP
--------------------

* About
* Overview of design
* List of features
* How to run
* Usage instructions
* Example script explanation
* Latest design changes
* Image citations

ABOUT
------

Our program aims to create an image-processing application that can be used to apply different
filters and effects to multiple layers of images. The program can hold one or more layers of images,
and the user is able to switch between the layers to see the results of applying different effects.

OVERVIEW OF DESIGN 
-------------------
Listed below are several of the classes implemented and their descriptions.

* Runner:
  - This class holds the main method to run the program.
  - Three types of commands are supported as of 6/27/21:
    - "script" - opens a given script, runs it, closes program
    - "text" - allows for interactive scripting within terminal
    - "interactive" - opens GUI and allows for interactions within it

* IFilterModelState:
  - The purpose of this interface is to define methods useful for observing information within a
  model.
  - This interface is used in the view to prevent the possibility of the view mutating the model.

* IFilterModel:
  - The purpose of this interface is to define the methods a proper model for the image processing
   app should have.
  - This interface is parameterized over the type of image representation the client chooses.

* IComplexEffectModel:
  - The purpose of this interface is to add new functionality to the existing IFilterModel interface
of accepting and applying effects without modifying the existing IFilterModel interface
  - We do this to minimize modification to the old interface and instead we extend the old interface
and add the new functionality in IComplexEffectModel

* FilterModel:
  -  This is our implementation of the IFilterModel interface.
  - This implementation is parameterized over the ImageInterface we have defined.
  - We chose to use a stack to keep track of image history.
  - The filters are facilitated through this class, but the actual action of filtering takes place
   outside the class.
  - Similarly, the model

* MockModel:
  - The mock model is used for testing so we can see if the controller is sending the model
the correct data when methods and actions are called
  - The mock implements IFilterModel so it can be plugged in in the controller

* IFilterController:
  - This purpose of this interface is to define methods for running the program and methods which deal
with user input then deciding exactly what to do with the user input and which methods in the model
to call when and how to use them.
  - This interface is parameterized over the type of image representation the client chooses.

* FilterController:
  - This is our implementation of IFilterController interface.
  - The class has a model and view field since it is a scanner and delegates to the model and the view
 when so applicable, and the controller also has a scanner field so no matter what method we choose
 to call we can refer to the scanner and the scanner does not get out of sync.
  - The controller runs and executes the program

* ControllerUtil:
  - Houses utility methods for the controller to import one or multiple images and
 export a single file or export all of the files in the multi layered image
  - The util class is used since we deemed these methods universal for all controller implementations
  - These methods are in the Controller of MVC since they deal with IO

* GUIController:
  - The GUIController is the controller for the program and the ActionListener
  - The GUIController uses command line design to delegate to helpers and other method
calls when the indicated ActionListener is passed
  - The GUIController delegates to the model for functionality and the view for displaying

* IFilterView:
  - The purpose of this interface is to define methods for displaying information. This interface was
  designed to be a simple textual representation of the current state of the model.
  - Defines a single method for rendering messages to the user.

* IGUIFunctionality:
  - The purpose of this interface is to add new functionality specific to GUI view implementations
to the existing IFilterView interface without modifying the old interface by extending IFilterView
  - This allows us to add GUI specific view methods without disrupting non gui existing or new
implementations of IFilterView

* SimpleFilterView:
  - This is our implementation of the IFilterView interface
  - This class has a constructor that takes in an Appendable and implements renderMessage so that the
  given message is added to this Appendable.

* GUIView:
  - This is our implementation of the IGUIFunctionality interface.
  - When constructed, the GUIView shows the information in the model in the form of a graphical user
  interface.
  - The GUIView is coupled to the model and changes as the model changes.
  - Interactions in the GUIView are sent to the GUIController to determine if the model should change.

* ILayer:
  - The purpose of this interface is to define methods useful for working with layers.
  - Many of these methods are useful for observing information within a layer which is a necessary
  part of importing and exporting.

* Layer:
  - This is our implementation of the ILayer interface.
  - This implementation gives layers three fields: an image, a name, and a visibility.
  - It contains several constructors. Each one is used depending on the amount of information known
  about a layer at the time it is contructed.

* ImageInterface:
  - The purpose of this interface is to define the methods a proper image implementation should have.
  - This interface includes methods for creating images, facilitating the filtering of images,
   importing images, and exporting images.

* Image:
  - This is our implementation of ImageInterface.
  - We chose to represent images as objects with three ArrayList fields,
   one that holds integer values for each color. The color values for any pixel in an image can be
   found by getting the value at that pixels "x" and "y" coordinates. The "x" would be the
   index in the outer list, while the "y" would be the index in the inner list.

* IFileType:
  - The purpose of this interface is to define methods that representations of file types must have
   in order for the application to support importing and exporting.
  - The way in which this importing and exporting is done is left up to the specific implementation.

* AbstractComplexFileType:
  - Abstracts the process of importing and exporting a file for JPEG and PNG file types
and any future abstract-able file types that we wish to include in our image editing

* PPM:
  - This is the implementation of the IFileType that allows for importing PPM files and exporting
   images as PPM files.
  - The methods in this class are where the action of importing and exporting PPM files
   actually takes place.

* JPEG:
  - This is the implementation of the IFileType that allows for importing JPEG files and exporting
   images as JPEG files.
  - The methods in this class are where the action of importing and exporting JPEG files
   actually takes place.

* PNG:
  - This is the implementation of the IFileType that allows for importing PNG files and exporting
   images as PNG files.
  - The methods in this class are where the action of importing and exporting PNG files
   actually takes place.

* IEffect:
  - The purpose of this interface is to define a method to apply a dimensional
 image modification action
  - This new interface is for lossy image modification functionality where producing values for pixels
pixel-by-pixel does not work since some pixels may no longer exist so instead we return the
new ImageInterface in its entirety

* AbstractApplyAllStatus:
  - Has a method that returns true if the effect which extends AbstractApplyAllStatus should be called
  - This abstract class implements IEffect so we can raise any common functionality amongst effects
to the abstract class in the future

* Downscale:
  - This class extends the AbstractApplyAllStatus class to suport returning the boolean representing
whether the method should be applied to all layers which in this case with downscale is true
  - Downscale shrinks an image to the specified new height and width, we lose some of the image data
in this process and Downscale performs all of the arithmetic and image pixel truncation to return
 the new image

* IMosaic:
  - Extends the old IFilter interface by not changing the old implementation but adding new
 functionality via extension and then adding the new method to initialize seeds
  - the Mosaic class implements IMosaic so Mosaic still "is-a" filter but has the necessary
 added functionality of the initializeSeed method which allows us to "prime" the Mosaic class for
 filtering

* Mosaic:
  - This class represents the mosaic filter on an image which generates a user defined
number of seeds and all of the pixels closet to said seed are then averaged and all assigned said
average value to create blotches of color and make the image look like a mosaic image

* IPosn:
  - The purpose of the interface is to define the methods for the Posn class and if we were to
later on decide on a different implementation of Posn then we would simply create a new
implementing class of IPosn

* Posn:
  - This class represents a pixel location in the image that we are mosaic-ing and associates
the x y pixel position with the seed that closest to it

* ISeed:
  - The purpose of the interface is to define the methods for the seed class and if we were to
later on decide on a different implementation of seed then we would simply create a new
implementing class of ISeed

* Seed:
  - Represents a randomly generated point in a mosaic image which will then form the cluster that
we find the average pixel value of and then set all of the pixels closest to the seed with the
average pixel values create the blotch looking groups in mosaic images

* SeedComparator:
  - Compares if this seed comes before that seed based off of the cartesian coordinates in
 the each seed respectively

* IFilter:
  - The purpose of this interface is to define a method needed to properly filter an image.
  - This method is used to find the new red, green, and blue values at an particular pixel.
  - The way in which these new values are found is left up to the implementation of the interface.

* AbstractColorFilter:
  - This class is used to abstract most of the implementation for applying color filters (monochrome
   and sepia as of 6/11/21).
  - Since the actual code for these implementations is identical, it can be placed in this abstract
   class. The only change comes in the transpose matrix being used, which is why this abstract class
   defines as abstract method meant to get the transpose matrix that is to be used for the
   filtering.

* Monochrome:
  - This class extends the AbstractColorFilter class to support applying a monochrome filter to an
   image.
  - It is implemented so that a monochrome transpose matrix is used in the filtering process.

* Sepia:
  - This class extends the AbstractColorFilter class to support applying a sepia filter to an
   image.
  - It is implemented so that a sepia transpose matrix is used in the filtering process.

* AbstractSquareMatrixMath:
  - This class is used to abstract most of the implementation for applying non-color filters (blur
   and sharpen as of 6/11/21).
  - The actual code for applying these filters is nearly identical, which is why it can be placed in
   this abstract class. The differentiation comes in the size of the transpose matrix, which is why
   each filter must have a class that extends this abstract class.
  - These extending classes will be able to properly set up the transpose matrix for their respective
   filter so that the filter can be applied at any point in the image.

* Blur:
  - This class extends the AbstractSquareMath class to support blurring an image.
  - The class defines the 3x3 Gaussian blur matrix that is to be applied to the image through code in
   the abstract class.
  - Since this matrix is specific to this implementation of blurring, it must be set up in this
   class.

* Sharpen:
  - This class extends the AbstractSquareMath class to support sharpening an image.
  - The class defines the 5x5 sharpening matrix that is to be applied to the image through code in
   the abstract class.
  - Since this matrix is specific to this implementation of sharpening,
   it must be set up in this class.

* ImageCreatorInterface:
  - The purpose of this interface is to define a method that is needed to create a data
   representation for a programmatically created image.
  - Implementing classes must decide how to create this representation based on the image they want
   to create.

* CheckerBoard:
  - This class implements the ImageCreatorInterface to support the creation of a data representation
   for a checkerboard image.
   - It uses the gives width, height, tileSize, and colors in a nested for loop that creates a
   checkerboard pattern.

* ProgrammaticImageCreator:
  - This is a factory class meant to create ImageCreatorInterface objects.
  - The class defines a public enum ProgrammaticImageType that contains enumerations for the types of
   images that can currently be created programmatically by the model.
  - As new images are needed, new enumerations can be added to the ProgrammaticImageType enum to
   support them.

* ColorEnum:
  - The purpose of this enum is to define the types of colors that can be used to create images
   programmatically.
  - Each color enumeration has three integer fields representing that color's red, green, and blue
   values.

LIST OF FEATURES
-----------------
Below is a list of features that are fully functional in our program and exposed to the user through
the GUI:
- creating layers
- removing layers
- setting a layer to current
- setting a layer to visible
- setting a layer to invisible
- creating a checkerboard image programmatically
- loading a single image into a pre-created layer
- loading several layers of images in a folder with a suitable locations.txt file containing details
  about each layer
- loading a script containing actions that are to be completed by the program
- saving a single image
- saving all layers of images to a folder and generating a locations.txt file containing details
  about each layer
- naming a layer
- blurring an image
- sharpening an image
- applying a sepia filter to an image
- applying a monochrome filter to an image
- applying a mosaic filter to an image
- downscaling an image

HOW TO RUN
-----------

Steps for running jar file:
  1. Open the terminal/command prompt.
  2. Navigate to folder containing jar file. This folder should also contain any scripts and images
     that are to be used in the program.
  3. Type one of the following command-line inputs (all other inputs are invalid as of 6/27/2021):
       - java -jar Program.jar -script path-of-script-file : when invoked in this manner the program
         should open the script file, execute it and then shut down.
       - java -jar Program.jar -text : when invoked in this manner the program should open in an
         interactive text mode, allowing the user to type the script and execute it one line at a
         time.
       - java -jar Program.jar -interactive : when invoked in this manner the program should open
         the graphical user interface.

USAGE INSTRUCTIONS
-------------------
Please see corresponding USEME.

EXAMPLE SCRIPT EXPLANATION
---------------------------
Filename: ExampleScript1.txt:
  1. create layer
  2. create layer
  3. create layer
  4. set third layer as current layer
  5. load original image of boston skyline
  6. apply sepia filter
  7. apply sepia filter
  9. sharpen image
  10. save filtered image
  11. set third layer to invisible
  12. set second layer as current layer
  13. load original image of boston skyline
  14. apply monochrome filter
  15. apply monochrome filter
  16. apply sharpen filter
  17. apply sharpen filter
  18. apply sharpen filter
  19. save filtered image
  20. set second layer to invisible
  21. set first layer as current layer
  22. load original image of boston skyline
  23. save original image as new image
  24. export all layers to testScript1Folder1
  25. import all layers from testScript1Folder1 back into the app
  26. create new layer
  27. set fourth layer as current layer
  28. set fourth layer to visible
  29. load original image of boston skyline
  30. downscale all layers to 400x400
  31. save topmost visible layer
  32. export all layers to testScript1Folder2

LATEST DESIGN CHANGES
----------------------

* Model
  - Downscaling effect added
    - The IComplexEffectModel interface added to support downscaling operation.
    - We initially tried to implement the operation as an IFilter class, which worked to a certain
  extent. When only applied to a single layer, the operation worked reasonably well. However, when
  applied to all layers as it is supposed to, the operation took a very long time to complete.
  This was due to the fact that all layers had to be downscaled in the Downscale constructor.
  To try and reuse as much as code as possible, we later had to loop through every image a second
  time to find and set the new color values.
    - While this allowed for more code reuse, we decided that the decrease in efficiency was not
  worth it. We decided to instead implement a new method in the that comes from IComplexEffectModel,
  applyComplexEffect.
    - IComplexEffectModel now extends our existing model interface, IFilterModel. Our model class
  now implements IComplexEffectModel to allow for this new feature.
    - We also created an IEffect interface designed to be used in the future with effects that require
  information about multiple layers or entire images before any of the computation involving the
  effect can be done.

  - Mosaic effect added
    - Mosaic feature was able to be added using existing IFilter interface. A Mosaic class was made that
  implements this interface. Objects of the class could then be passed into the existing applyFilter
  method in the model. No design changes were required to implement this feature.
  
  - Examples of the above effects can be seen in the res folder.

* Controller
  - Additional GUIController made to handle interactions in the GUI. This controller creates a GUIView
  that the user can interact with. The controller tells the view what to display and when, as well
  as prompts the model for information at the appropriate time.

* View
  - Addition GUIView made to display images and menu options to user. The user can interact with all
  features of the model through this GUI.

IMAGE CITATIONS
---------------

* Boston skyline image
  - https://www.incimages.com/uploaded_files/image/970x450/getty_471698707_243671.jpg
