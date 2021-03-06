# RGP-ELASTIC

## 构建环境

- 构建自定义的`ElasticSearch`镜像

    ```
    docker build -f Dockerfile -t 201210704116/elasticsearch .
    ```

- 第一步生成证书相关文件

    ```
    docker-compose -f create-certs.yml up
    ```

- 将`.env`文件中的`SECURITY_ENABLED`设置成`false`，启动容器到`Kibana`平台上传证书

    ```
    docker-compose --compatibility up -d
    ```

- 上传证书破解成功后将`.env`文件中的`SECURITY_ENABLED`设置成`true`重新启动。并且进入到运行的`es`容器的`${ES_HOME}/bin`目录下执行命令`elasticsearch-setup-passwords interactive`生成密码，这样既可完成单机版本的`ES`集群+`Kibana`+`Cerebro`环境搭建，且带认证

**关于Docker volume目录挂载权限可参考[Docker挂载volume的用户权限问题](https://www.cnblogs.com/woshimrf/p/understand-docker-uid.html)核心在于容器与宿主机共享UID**

## 创建客户端用户

- 创建角色

    ![image-20200512152801714](images/README/image-20200512152801714.png)

- 创建用户

    ![image-20200512152830440](images/README/image-20200512152830440.png)

