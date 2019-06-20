# scoubidou project diagram 

A program for creating 2D diagrams that shows how to weave any rectangular stitch.

Current file to be use is scoubidou4.

By running StitchWeavingForTutorial with a given input a,b, firstLineEndPoint, crissNumberOfLines the output images will be something like:
<p align="center">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/0.png" width="200" title="hover text">
    <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/1.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/2.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/3.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/4.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/5.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/6.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/7.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/8.png" width="200" title="hover text">
</p>

By running printStitch it'll give all final diagram outputs such as if I set a=3 and b=3, these will be some of the outputs.
Note that some output still need some correction, the algorithm I wrote doesn't know how to deal with these issuse. So  version 1.0 we will need to implement a correction that will be done manually (by changing manually the location of the lines of each stitch).
<p align="center">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/basicStitch_11_3_3X3.png" width="200" title="hover text">
    <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/basicStitch_11_5_3X3.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/basicStitch_13_5_3X3.png" width="200" title="hover text">

  
  <p align="center">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/basicStitch_19_1_3X3.png" width="200" title="hover text">
    <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/basicStitch_15_3_3X3.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/basicStitch_15_1_3X3.png" width="200" title="hover text">
  <img src="https://github.com/ysetbon/scoubidou-project-diagram-/blob/master/images/basicStitch_13_4_3X3.png" width="200" title="hover text">
  </p>

