# 1. Compile the entire project to the bin directory
javac -d bin -sourcepath src src/de/hsh/Main.java

# 2. Copy the resource directories (images and audio) to the bin directory so the app can find them
cp -r src/de/hsh/images bin/de/hsh/
cp -r src/de/hsh/audio bin/de/hsh/

# 3. Run the application
java -cp bin de.hsh.Main