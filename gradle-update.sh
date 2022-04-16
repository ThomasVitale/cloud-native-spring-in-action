#!/bin/bash

CHAPTERS=$(ls)
for CHAPTER in $CHAPTERS
do
   if [ -d $CHAPTER ]
   then
      cd $CHAPTER
      PROJECTS=$(ls)
      for PROJECT in $PROJECTS
      do
         if [ -d PROJECT ]
         then
            cd $PROJECT && ./gradle clean
         fi
      done
   fi
done
