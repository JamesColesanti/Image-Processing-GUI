IMAGE PROCESSING APP USAGE INSTRUCTIONS
---------------------------------------

* GUI instructions
* CLI instruction 

GUI INSTRUCTIONS
----------------

- When opened one needs to go to the "Layer Action" and click "Create"; this creates a layer
- Go to "Load Actions" where the use can then select...
  -- "Load" and then navigate to the file we want to load
  -- or we can click "Load all" which will load all of the images in the folder selected as long as
  the folder selected contains a properly formatted locations.txt file
  -- or we can click "Load script" where we will then navigate to the script file that we want to
  execute in the program and run those commands
- If we do not want to load a pre-existing image we can click the menu bar "Programmatic" and
then click "Checkerboard"
-- From here we input the height, width, and tile size of our checkerboard
- Now that we have an image uploaded we can Filter the image by going to the "Filters" menu and
then select a filter...
-- We can select "Blur" to blur the current top most image
-- We can select "Sharpen" to sharpen the current top most image
-- We can select "Sepia" to apply the sepia filter to the top most image
-- We can select "Monochrome" to apply the monochrome filter to the top most image
-- We can select "Mosaic" and then input a number of seeds, in the popup window, >= 0
- If we want to apply an effect to the image we can select the "Effect" menu and then
select an effect...
-- We can select "Downscale" and then input a correct new width, in the popup window, of a value
that is <= the current width, and then input a correct new height in the popup window, of a value
that is <= the current height
- We can perform additional layer actions by selecting the "Layer actions" menu and we can
then select...
-- "Remove" and then input the layer we want to remove from the image at the specified index
-- or we can select "Visible" and then input the layer we want to make have true visibility
-- or we can select "Invisible" and then input the layer we want to make have false visibility
-- or we can select "Create" and creates a new top most visible layer
-- or we can select "Current" and then input the layer we want to make the current layer displayed
- We can continue to do the all of the above actions, and then when we want to save or export
we click the "Save actions" menu and we can then click...
-- "Name layer" and then input what we want the name of the current layer
-- "Save" and then navigate to where we want to export the file and with what name and file type
 we want to export with
-- "Save all" and then navigate to the folder where we want to export all the files to, or create
a new folder where we want to export all of the files to

CLI INSTRUCTIONS
----------------

- q or Q: quit
   - Example: "q", "Q"
   - Conditions: none
- blur: blurs current image
   - Example: "blur"
   - Conditions: must have previously loaded an image into the current layer
- sepia: applies sepia to current image
   - Example: "sepia"
   - Conditions: must have previously loaded an image into the current layer
- sharpen: sharpens current image
   - Example: "sharpen"
   - Conditions: must have previously loaded an image into the current layer
- monochrome: applies monochrome to the current image
   - Example: "monochrome"
   - Conditions: must have previously loaded an image into the current layer
- downscale: applies the downscale effect to all layers of the image
   - Example: "downscale 100 100"
   - Conditions: must have previously load at least 1 image and the new height and
   width inputted can not be larger than the current height and width of the image
- mosaic: applies the mosaic effect to the current top most visible image
   - Example: "mosaic 100"
   - Condition: must have a current top most visible image and the number of seeds
   inputted must be non-negative
- create: adds a new empty layer
   - Example: "create"
   - Conditions: none
- current INT: int corresponds to the layer that we want to make visible and top
   - Example: "current 1", "current 2"
   - Conditions:
     - integer must be typed after the word current
     - integer cannot be negative and must be within the range of possible values based on the
       amount of layers currently in the model
- load FOLDER/.../FOLDER/FILENAME.FILETYPE: loads the file into the lowest empty layer
   - Example: "load Checkerboard.png", "load Checkerboard.jpeg"
   - Conditions:
     - must have previously created a layer for the image to be loaded into
     - file path must be valid
     - exact file path is necessary
     - EXCEPTION: if JAR is in same folder as the images the user is attempting to import, only the
       name of the file is needed, not the exact file path
- save FILENAME.FILETYPE: saves the current image and exports
   - Example: "save Checkerboard.ppm", "save manhattan.png"
   - Conditions:
     - file path must be valid
     - exact file path is necessary
     - EXCEPTION: if JAR is in same folder as the images the user is attempting to import, only the
       name of the file is needed, not the exact file path
- export-all FOLDER/.../FOLDER/: exports all of the layers to the location
      with a locations.txt file
   - Example: "export-all testFolder"
   - Conditions:
     - file path must be valid
     - exact file path is necessary
     - EXCEPTION: if JAR is in same folder as the images the user is attempting to import, only the
       name of the file is needed, not the exact file path
     - in order to export all layers, each layers must have been saved individually previously
- import-all FOLDER/.../FOLDER/: imports all of the in the folder based off of the locations.txt
      file in the specified folder
   - Example: "import-all testFolder"
   - Conditions:
     - file path must be valid
     - exact file path is necessary
     - EXCEPTION: if JAR is in same folder as the images the user is attempting to import, only the
       name of the file is needed, not the exact file path
- invisible INDEX(1 indexing): sets the layer at the index to invisible
   - Example: "invisible 1", "invisible 2"
   - Conditions: must be followed by an integer
- visible INDEX(1 indexing): sets the layer at the index to visible
   - Example: "visible 1, visible 2"
   - Conditions: must be followed by an integer
- checkerboard: tells the model to create an image of a checkerboard programmatically
   - Example: "checkerboard 3 3 1"
   - Conditions: must be followed by image height, width, and tileSize
