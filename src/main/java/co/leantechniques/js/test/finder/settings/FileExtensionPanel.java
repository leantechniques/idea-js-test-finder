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
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.AddEditDeleteListPanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class FileExtensionPanel extends JPanel {
    private ExtensionsPanel listPanel;

    public FileExtensionPanel(String title, final Listener listener) {
        super(new BorderLayout());
        listPanel = new ExtensionsPanel(title, listener);
        add(listPanel, BorderLayout.CENTER);
    }

    public void clear() {
        listPanel.clear();
    }

    public void addAll(List<String> extensions) {
        listPanel.addAll(extensions);
    }

    public List<String> getExtentions() {
        ArrayList<String> extensions = new ArrayList<String>();
        for (Object o : listPanel.getListItems()) {
            extensions.add(o.toString());
        }
        return extensions;
    }

    public interface Listener {
        void modified();
    }

    private class FileExtensionCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel component = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String extension = (String) value;

            StringBuilder builder = new StringBuilder();
            builder.append("<html>");
            builder.append("<i>").append("{").append(PluginBundle.message("configurator.filename.placeholder")).append("}").append("</i>");
            builder.append(extension);
            builder.append("</html>");

            component.setText(builder.toString());

            return component;
        }
    }

    private class ExtensionsPanel extends AddEditDeleteListPanel<String> {
        public ExtensionsPanel(String title, final Listener listener) {
            super(title, new ArrayList<String>());
            myList.getModel().addListDataListener(new ListDataListener() {
                @Override
                public void intervalAdded(ListDataEvent e) {
                    listener.modified();
                }

                @Override
                public void intervalRemoved(ListDataEvent e) {
                    listener.modified();
                }

                public void contentsChanged(ListDataEvent e) {
                    listener.modified();
                }
            });
        }

        @Nullable
        @Override
        protected String editSelectedItem(String o) {
            return Messages.showInputDialog(this,
                    PluginBundle.message("configurator.edit.file.extension.message"),
                    PluginBundle.message("configurator.edit.file.extension.title"),
                    null, o, null);

        }

        @Nullable
        @Override
        protected String findItemToAdd() {
            return Messages.showInputDialog(this,
                    PluginBundle.message("configurator.add.file.extension.message"),
                    PluginBundle.message("configurator.add.file.extension.title"),
                    null, "", null);

        }

        @Override
        protected ListCellRenderer getListCellRenderer() {
            return new FileExtensionCellRenderer();
        }

        public void clear() {
            myListModel.clear();
        }

        public void addAll(List<String> extensions) {
            for (String extension : extensions) {
                myListModel.addElement(extension);
            }
        }
    }
}
