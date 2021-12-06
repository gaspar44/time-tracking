#!/bin/bash
## Compilation
SCRIPT_DIR="$PWD/.."
disable_compilation=false
image_name="time_tracker"

function print_usage() {
    echo "--disable-compilation         Stops from generating new binary"
    echo "--help, -h                    Show this help and exits "
}

for arg in "$@"
do
    case $arg in
        --disable-compilation)
            disable_compilation=true
            shift
            ;;
        --help|-h)
            print_usage
            exit 0
            ;;
        *)
        print_usage
        exit 1
        ;;
    esac
done

if ! ${disable_compilation} ; then
    echo "- Compiling"
    docker run --rm -u gradle -v "${SCRIPT_DIR}":/home/gradle/project -w /home/gradle/project -ti gradle:7.2.0 gradle clean installDist
fi  

## Execution
if [[ "$(docker images -q ${image_name} 2> /dev/null)" == "" ]]; then
    echo " - Generatin time tracker docker"
    docker build -t ${image_name} .
fi

execution_dir="${SCRIPT_DIR}/build/install/run"
container_name="time-tracker"
echo "- Creating docker server"
docker run --name time-tracker -p 8080:8080 -v ${execution_dir}:${execution_dir} -w ${execution_dir} -d ${image_name}
