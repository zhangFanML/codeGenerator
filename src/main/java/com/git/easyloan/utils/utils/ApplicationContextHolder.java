package com.git.easyloan.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextHolder implements ApplicationContextAware {
    private static Logger log = LoggerFactory.getLogger(ApplicationContextHolder.class);
    private static ApplicationContext applicationContext;

    public ApplicationContextHolder() {
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (applicationContext != null) {
            throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
        } else {
            applicationContext = context;
            log.info("holded applicationContext,displayName:" + applicationContext.getDisplayName());
        }
    }

    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("'applicationContext' property is null,ApplicationContextHolder not yet init.");
        } else {
            return applicationContext;
        }
    }

    public static String printAllBeans() {
        String[] beans = getApplicationContext().getBeanDefinitionNames();
        StringBuffer beanStr = new StringBuffer();
        String[] var2 = beans;
        int var3 = beans.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String bean = var2[var4];
            beanStr.append(bean + ",");
        }

        return beanStr.toString();
    }

    private static Object getBean(String beanName) throws Exception {
        try {
            Object bean = getApplicationContext().getBean(beanName);
            if (bean == null) {
                throw new Exception("框架错误：获取[" + beanName + "]失败，请检查名称是否正确！");
            } else {
                return bean;
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            throw new Exception("框架错误：获取[" + beanName + "]失败，错误原因：" + var2.getMessage());
        }
    }

    private static String strs2str(String[] strs) {
        StringBuffer sb = new StringBuffer();
        String[] var2 = strs;
        int var3 = strs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String str = var2[var4];
            sb.append(str + ",");
        }

        return sb.toString();
    }

    public static Object getServiceBean(String serviceName) {
        try {
            if (getCaller().indexOf("Service") > 0) {
                throw new Exception("框架错误：框架不允许在Service层调用其他Service服务，请检查代码是否正确！调用者为：" + getCaller());
            } else {
                Object bean = getBean(serviceName);
                if (bean == null) {
                    throw new Exception("框架错误：获取[" + serviceName + "]失败，请检查名称是否正确！");
                } else {
                    return bean;
                }
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Object getCoreBean(String coreName) {
        try {
            if (getCaller().indexOf("Service") < 0 && getCaller().indexOf("Manager") < 0 && getCaller().indexOf("Flow") < 0) {
                throw new Exception("框架错误：框架只允许在services层、Manager层或Flow层调用Core原子服务，请检查代码是否正确！调用者为：" + getCaller());
            } else {
                Object bean = getBean(coreName);
                if (bean == null) {
                    throw new Exception("框架错误：获取[" + coreName + "]失败，请检查名称是否正确！");
                } else {
                    return bean;
                }
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Object getManagerBean(String managerName) {
        try {
            Object bean = getBean(managerName);
            if (bean == null) {
                throw new Exception("框架错误：获取[" + managerName + "]失败，请检查名称是否正确！");
            } else {
                return bean;
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Object getDaoBean(String daoName) {
        try {
            if (getCaller().indexOf("Manager") < 0) {
                throw new Exception("框架错误：框架只允许在action层调用Service服务，请检查代码是否正确！");
            } else {
                Object bean = getBean(daoName);
                if (bean == null) {
                    throw new Exception("框架错误：获取[" + daoName + "]失败，请检查名称是否正确！");
                } else {
                    return bean;
                }
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Object getChecker(String checkerName) {
        try {
            if (checkerName.indexOf(".") > 0) {
                checkerName = checkerName.substring(checkerName.lastIndexOf(".") + 1);
                checkerName = checkerName.substring(0, 1).toLowerCase() + checkerName.substring(1);
            }

            Object bean = getBean(checkerName);
            if (bean == null) {
                throw new Exception("框架错误：获取菜单检查方法[" + checkerName + "]失败，请检查名称是否正确！");
            } else {
                return bean;
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String getCaller() {
        String caller = JDKUtils.getCaller(3);
        return caller.substring(0, caller.indexOf("."));
    }

    public static void cleanHolder() {
        applicationContext = null;
    }

    public static void main(String[] args) {
        System.out.println(getCaller());
    }
}
