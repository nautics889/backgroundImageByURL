# Background Image By URL
This plugin set background to a random image specified in selected file.

The project is forked from [a similar one](https://github.com/HNUHell/backgroundImagePlus) 
with addition of certain changes.

![Alt text](./resources/screenshots/example.png?raw=true "Title")

### Settings

Settings -> Appearance & Behaviour -> Background Image By Url  
- File with URLs: set file which contains URLs to images
- Change every: set interval to change background every x seconds/minutes/hours/days
- Change mode: set environment for change （if choose "both", then "Keep same image" will keep same image in both environments）

![Alt text](./resources/screenshots/settings.png?raw=true "Title")

### Menu Items

View: 
- Set Background Image (set image properties : opacity/fill/anchor/flip ...)
- Random Background Image
- Random Order Reset (regenerate a new round of image order based on the last file path)
- Clear Background Image (cancels "change every" interval)

![Alt text](./resources/screenshots/actions.png?raw=true "Title")

### Usage

- Set file with URLs (each row in the file has to represent a single URL)
- Optionally set "change every" interval
- Optionally bind "Random Background Image" action to a hotkey
- Optionally bind "Random Order Reset" action to a hotkey
- Cycle background images while procrastinating

*NOTE:* to set background opacity or other image properties you can either do this in editor as usual 
or set it after URL using ',' delimiter in a file like this:
```https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Puma_face.jpg/1280px-Puma_face.jpg,40```
For example such row corresponds an image with 40% opacity.
