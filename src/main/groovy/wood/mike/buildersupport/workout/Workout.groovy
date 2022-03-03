package wood.mike.buildersupport.workout

import groovy.transform.ToString

@ToString(includePackage = false)
class Workout {
    Warmup warmup
    List<Exercise> exercises = []
}
