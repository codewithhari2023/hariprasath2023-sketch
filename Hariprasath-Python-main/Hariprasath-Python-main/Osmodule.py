import os 
import logging
logging.basicConfig(filename='folder_creation.log', level=logging.INFO, format='%(asctime)s - %(message)s')


def createfolder(foldername):
    try:
        if not os.path.exists(foldername):
            os.mkdir(foldername)
        logging.info("folder created sucessfully")
    except:
        logging.info("folder failed to crreated")
def createfile(foldername,filename):
    try:
        filepath  = os.path.join(foldername, filename)
        if os.path.exists(filepath):
            os.remove(filepath)
        else:
            with open(filepath,'w') as file:
                file.write("hello my name is hariprasath im 22 fine and im")
                logging.info("file created sucessfully")
                logging.info("content written sucessfully")
        
    except:
        logging.info("file failed to create")


if __name__ == "__main__":
    
    createfile("myfolder4","content2.txt")
