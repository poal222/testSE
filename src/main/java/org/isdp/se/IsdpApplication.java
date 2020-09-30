package org.isdp.se;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.configuration.ProcessEngineConfiguration;
import com.alibaba.smart.framework.engine.configuration.impl.DefaultProcessEngineConfiguration;
import com.alibaba.smart.framework.engine.configuration.impl.DefaultSmartEngine;
import com.alibaba.smart.framework.engine.constant.RequestMapSpecialKeyConstant;
import com.alibaba.smart.framework.engine.extension.scanner.SimpleAnnotationScanner;
import com.alibaba.smart.framework.engine.model.assembly.ProcessDefinition;
import com.alibaba.smart.framework.engine.model.instance.ProcessInstance;
import com.alibaba.smart.framework.engine.service.command.ExecutionCommandService;
import com.alibaba.smart.framework.engine.service.command.ProcessCommandService;
import com.alibaba.smart.framework.engine.service.command.RepositoryCommandService;
import com.alibaba.smart.framework.engine.service.query.ExecutionQueryService;
import com.alibaba.smart.framework.engine.service.query.ProcessQueryService;
import com.alibaba.smart.framework.engine.service.query.RepositoryQueryService;
import org.isdp.se.config.DefaultTaskAssigneeDispatcher;
import org.isdp.se.constant.IsdpBpmAssigneeTypeConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan(basePackages = "com.alibaba.smart.framework.engine.persister")
@ComponentScan(basePackages = {
		"com.alibaba.smart.framework.engine",
		"org.isdp.se.config",
})
public class IsdpApplication {
	public static void main(String[] args) {
		SpringApplication.run(IsdpApplication.class, args);
		IsdpApplication isdpApplication = new IsdpApplication();
		isdpApplication.mockCreateOrder();
	}


	@Autowired
	private SmartEngine smartEngine;
	public  void mockCreateOrder(){


		 ProcessEngineConfiguration processEngineConfiguration = new DefaultProcessEngineConfiguration();

		 SmartEngine smartEngine = new DefaultSmartEngine();

		//3. 部署流程定义
		 RepositoryCommandService repositoryCommandService;
		//2.获得常用服务
		 ProcessCommandService processCommandService;
		 ProcessQueryService processQueryService;

		 ExecutionQueryService executionQueryService;
		 ExecutionCommandService executionCommandService;
		 RepositoryQueryService repositoryQueryService ;

		 SimpleAnnotationScanner simpleAnnotationScanner ;

		simpleAnnotationScanner = new SimpleAnnotationScanner(SmartEngine.class.getPackage().getName());




		processEngineConfiguration = new DefaultProcessEngineConfiguration();
		// 配置taskAssigneeService
		processEngineConfiguration.setTaskAssigneeDispatcher(new DefaultTaskAssigneeDispatcher());

		smartEngine = new DefaultSmartEngine();
		smartEngine.init(processEngineConfiguration);

		//3. 部署流程定义
		repositoryCommandService = smartEngine
				.getRepositoryCommandService();
		repositoryQueryService = smartEngine
				.getRepositoryQueryService();

		processCommandService = smartEngine.getProcessCommandService();
		processQueryService = smartEngine.getProcessQueryService();
		executionQueryService = smartEngine.getExecutionQueryService();
		executionCommandService = smartEngine.getExecutionCommandService();





		ProcessDefinition processDefinition = repositoryCommandService
				.deploy("smart-engine/parallel-gateway-usertask-process.bpmn").getFirstProcessDefinition();
//		assertEquals(16, processDefinition.getBaseElementList().size());
		System.out.println(processDefinition.getId());
		System.out.println(processDefinition.getName());
		System.out.println(processDefinition.getVersion());
		System.out.println(processDefinition.getBaseElementList());
		System.out.println(processDefinition.getBaseElementList().size());


		//4.启动流程实例
		Map<String, Object> request = new HashMap<String, Object>();
		// 配置参数
		request.put("user","admin");
		request.put("user","admin");
		request.put(IsdpBpmAssigneeTypeConstant.USER,"admin");
		request.put(RequestMapSpecialKeyConstant.PROCESS_INSTANCE_START_USER_ID,"123");
		request.put(RequestMapSpecialKeyConstant.PROCESS_DEFINITION_TYPE,"group");
		request.put("processVariable","processVariableValue");
		//4.启动流程实例
		ProcessInstance processInstance = processCommandService.start(processDefinition.getId(), processDefinition.getVersion());
//		ProcessInstance processInstance = processCommandService.start(
//				processDefinition.getId(), processDefinition.getVersion(), request
//		);
//		System.out.println(processDefinition.getProperties());
	}
}
