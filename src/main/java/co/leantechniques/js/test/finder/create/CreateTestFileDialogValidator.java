/**
 *
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package co.leantechniques.js.test.finder.create;

import co.leantechniques.js.test.finder.PluginBundle;
import co.leantechniques.js.test.finder.ui.DialogInputValidator;
import com.google.common.base.Optional;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ValidationInfo;

import java.io.File;

public class CreateTestFileDialogValidator implements DialogInputValidator<CreateTestFileDialog> {
    @Override
    public Optional<ValidationInfo> validate(Project project, CreateTestFileDialog dialog) {
        String destinationDirectoryPath = dialog.getDestinationDirectory();
        File testDirPath = new File(project.getBasePath(), destinationDirectoryPath);
        File testFile = new File(testDirPath, dialog.getFilename() + "." + dialog.getFilenameExtension());
        if (testFile.exists()) {
            return Optional.of(new ValidationInfo(
                    PluginBundle.message("error.file.already.exists"),
                    dialog.getFilenameComponent()
            ));
        }
        return Optional.absent();
    }
}
