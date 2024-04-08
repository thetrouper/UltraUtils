#!/bin/bash

# Run Gradle build
./gradlew build

# Check if the build was successful
if [ $? -eq 0 ]; then
    echo "Gradle build successful."

    # SFTP upload
    SFTP_HOST="home-server"
    SFTP_USER="trouper"
    SFTP_REMOTE_DIR="/home/trouper/minecraft/data/plugins/"

    # Create a temporary file with a unique name
    TEMP_FILE=$(mktemp)

    # Specify the local file to upload
    LOCAL_FILE="/run/media/trouper/'1TB drive'/IJ/IdeaProjects/UltraUtils/build/libs/UltraUtils-0.0.1.jar"

    # Write the SFTP commands to the temporary file
    echo "put $LOCAL_FILE $SFTP_REMOTE_DIR" > "$TEMP_FILE"
    echo "bye" >> "$TEMP_FILE"

    # Use sftp non-interactively with the specified commands
    sftp -oStrictHostKeyChecking=no -P 689 -oBatchMode=no -b "$TEMP_FILE" "$SFTP_USER@$SFTP_HOST"

    # Remove the temporary file
    rm -f "$TEMP_FILE"

    # SSH command to reload the plugin on the host
    #SSH_COMMANDS=(
    #    "pm reload UltraUtils"
    #    "execute at @a run playsound minecraft:entity.experience_orb.pickup master @a \~ \~ \~ 100 1 1"
    #    "tellraw @a '\"'[Server] Reload Complete, Upload Successful.'\"'"
    #)

    #for cmd in "${SSH_COMMANDS[@]}"; do
    #   ssh -oStrictHostKeyChecking=no -oBatchMode=no "$SFTP_USER@$SFTP_HOST" "docker exec docker_mc_1 mc-send-to-console $cmd"
    #done

    echo "Plugin Uploaded."
else
    echo "Gradle build failed."
fi
