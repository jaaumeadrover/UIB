"""
Utils: this python script has all auxiliary functions needed in other noteeboks.
AUTHOR: Joan Balaguer, Jaume Adrover
CREATION DATE: 2023-11-08
"""

from FeatureTransformer import FeatureTransformer
from itertools import chain
import skimage.feature
import numpy as np
import os
import cv2

"""
Function `process_images_in_folder`:
·PARAMS:
1. featureTransformer
2. customParams[]=[skimage.feature.*,params]
3. class_folder_path
4. class_folder_name
5. x_dict
6. y_dict
·FUNCTIONALITY:
Extracts features from all images of a specified folder,
inserting them into x_dict(feature extr. result) and y_dict(class_folder_name).
"""
def process_images_in_folder(featureTransformer,imgFeature,class_folder_path,class_folder_name,x_dict,y_dict):
    if os.path.isdir(class_folder_path):
        # Iterate through all the image files in the class folder
        for filename in os.listdir(class_folder_path):
            if filename.endswith(('.jpg', '.jpeg', '.png', '.bmp', '.gif')):
                # Read the image using OpenCV
                image_path = os.path.join(class_folder_path, filename)
                image = cv2.imread(image_path)
                image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) # Convert image to Black-Gray Scale
                image=image/255 # Normalize matrix values between 0 and 1
                if image is not None:
                    # Extract custom feature
                    feature = featureTransformer.transform(image=image,method=imgFeature[0],params=imgFeature[1])
                    x_dict.append(feature)
                    y_dict.append(class_folder_name.lower())

"""
Function `process_images_in_folder2`:
·PARAMS:
1. featureTransformer
2. customParams[]=[skimage.feature.*,params,skimage.feature.*,params2]
3. class_folder_path
4. class_folder_name
5. x_dict
6. y_dict
·FUNCTIONALITY:
Extracts specified 2 features from all images of specified folder, inserting them
into x_dict(feature extr. result) and y_dict(class_folder_name)
"""                    
def process_images_in_folder2(featureTransformer, imgFeature, class_folder_path, class_folder_name, x_dict, y_dict):
    if os.path.isdir(class_folder_path):
        # Iterate through all the image files in the class folder
        for filename in os.listdir(class_folder_path):
            if filename.endswith(('.jpg', '.jpeg', '.png', '.bmp', '.gif')):
                # Read the image using OpenCV
                image_path = os.path.join(class_folder_path, filename)
                image = cv2.imread(image_path)
                image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)  # Convert image to Black-Gray Scale
                image = image / 255  # Normalize matrix values between 0 and 1
                if image is not None:
                    # Extract custom feature
                    featureHOG = featureTransformer.transform(image=image, method=imgFeature[0], params=imgFeature[1])
                    featureCANNY = featureTransformer.transform(image=image, method=imgFeature[2], params=imgFeature[3])
                    # Combine features into a single list
                    features_list = np.concatenate((featureHOG.flatten(), featureCANNY.flatten()),axis=None)
                    
                    x_dict.append(features_list)
                    y_dict.append(class_folder_name.lower())
"""
Function `resize_images_in_folder`:
·PARAMS:
1. class_folder_path
2. target_width
3. target_height
·FUNCTIONALITY:
Resizes all images of a specified folder, to desired (target_width x target_height).
"""
def resize_images_in_folder(class_folder_path,target_width,target_height):
    if os.path.isdir(class_folder_path):
        # Iterate through all the image files in the class folder
        for filename in os.listdir(class_folder_path):
            if filename.endswith(('.jpg', '.jpeg', '.png', '.bmp', '.gif')):
                # Read the image using OpenCV
                image_path = os.path.join(class_folder_path, filename)
                image = cv2.imread(image_path)

                if image is not None:
                    # Resize the image to the target size (200x200)
                    resized_image = cv2.resize(image, (target_width, target_height))
                    
                    # Save the resized image back to the same path
                    cv2.imwrite(image_path, resized_image)



"""
Function `calculte_min_size`:
·PARAMS:
1. class_folder_path
·FUNCTIONALITY:
Calculates minimum width and height of images inside specified folder.
"""
def calculate_min_size(class_folder_path):
    class_min_width = float('inf')
    class_min_height = float('inf')

    if os.path.isdir(class_folder_path):
        # Iterate through all the image files in the class folder
        for filename in os.listdir(class_folder_path):
            if filename.endswith(('.jpg', '.jpeg', '.png', '.bmp', '.gif')):
                # Read the image using OpenCV
                image_path = os.path.join(class_folder_path, filename)
                image = cv2.imread(image_path)

                if image is not None:
                    # Get the dimensions of the image
                    height, width, _ = image.shape
                    class_min_width = min(class_min_width, width)
                    class_min_height = min(class_min_height, height)

        return class_min_width, class_min_height