package io.jenkins.plugins.jobtag.TagConfigure


def f = namespace(lib.FormTagLib)

f.section(title:_("Job Tag Configure")) {
    f.entry() {
        f.checkbox(name: "caseSenstive", title: _('Case Sensitive'), checked: instance.caseSenstive) {
        }
    }
    f.entry() {
        f.checkbox(name: "disableColor", title: _('Disable Colorful Mode'), checked: instance.disableColor) {
        }
    }

}
