/**
 * Copyright to the original author or authors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package co.leantechniques.js.test.finder.create;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

public class ProjectDirectoryMaker {
    public VirtualFile make(Project project, String destinationDirectory) {
        VirtualFile dir = project.getBaseDir();
        String[] parts = destinationDirectory.split("/|\\\\");
        for (String part : parts) {
            try {
                VirtualFile child = dir.findChild(part);
                if (child == null) {
                    dir = dir.createChildDirectory(project, part);
                } else {
                    dir = child;
                }
            } catch (IOException e) {
                new FailedToMakeDirectoryException(part, e);
            }
        }
        return dir;
    }

    private class FailedToMakeDirectoryException extends RuntimeException {
        public FailedToMakeDirectoryException(String dir, Throwable cause) {
            super("Failed to make directory [" + dir + "]", cause);
        }
    }
}
