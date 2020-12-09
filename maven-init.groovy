import hudson.model.*;
import jenkins.model.*;
import hudson.tools.*;
import hudson.tasks.Maven.MavenInstaller;
import hudson.tasks.Maven.MavenInstallation;

// 取得Jenkins实例
def instance = Jenkins.getInstance()

def mavenVersion = '3.5.2'
// 拿到maven插件在Jenkins中的实例
def mavenTool = instance.getDescriptor("hudson.tasks.Maven")
def mavenInstallations = mavenTool.getInstallations()
def mavenInstaller = new MavenInstaller(mavenVersion)
def installSourceProperty = new InstallSourceProperty([mavenInstaller])
// 配置maven插件
def name= "jenkins-book-mvn-" + mavenVersion
def maven_inst = new MavenInstallation(
  name, // Name
  "", // Home
  [installSourceProperty]
)
mavenInstallations += maven_inst
mavenTool.setInstallations((MavenInstallation[]) mavenInstallations)
mavenTool.save()

// 保存配置
instance.save()