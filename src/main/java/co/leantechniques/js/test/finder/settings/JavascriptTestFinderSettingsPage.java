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
package co.leantechniques.js.test.finder.settings;

import co.leantechniques.js.test.finder.PluginBundle;
import com.intellij.openapi.options.BaseConfigurableWithChangeSupport;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

public class JavascriptTestFinderSettingsPage extends BaseConfigurableWithChangeSupport implements SearchableConfigurable {
    private SettingsRepository settingsRepository;
    private FileExtensionPanel.Listener listener = new FileExtensionPanel.Listener() {
        public void modified() {
            setModified(true);
        }
    };
    private FileExtensionPanel productionExtensionsPanel;
    private FileExtensionPanel testExtensionsPanel;

    public JavascriptTestFinderSettingsPage(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public String getDisplayName() {
        return PluginBundle.message("configurator.display.name");
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }


    @Nullable
    @Override
    public JComponent createComponent() {
        PanelBuilder builder = new PanelBuilder(new FormLayout(
                "pref:grow",
                "pref, 3dlu, pref"
        ));
        CellConstraints cc = new CellConstraints();

        productionExtensionsPanel = new FileExtensionPanel(PluginBundle.message("configurator.production.file.panel.title"), listener);
        testExtensionsPanel = new FileExtensionPanel(PluginBundle.message("configurator.test.file.panel.title"), listener);

        builder.add(productionExtensionsPanel, cc.rc(1, 1));
        builder.add(testExtensionsPanel, cc.rc(3, 1));

        return builder.getPanel();
    }

    @Override
    public void apply() throws ConfigurationException {
        settingsRepository.updateProductionExtensionsTo(productionExtensionsPanel.getExtentions());
        settingsRepository.updateTestExtensionsTo(testExtensionsPanel.getExtentions());
        setModified(false);
    }

    @Override
    public void reset() {
        productionExtensionsPanel.clear();
        testExtensionsPanel.clear();

        productionExtensionsPanel.addAll(settingsRepository.getProductionExtensions());
        testExtensionsPanel.addAll(settingsRepository.getTestExtensions());
        setModified(false);
    }

    @Override
    public void disposeUIResources() {

    }

    @NotNull
    @Override
    public String getId() {
        return "js.test.finder.settings";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }
}
