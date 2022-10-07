package com.spring.reactive.excpetion;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class APIExcpetionHandler extends AbstractErrorWebExceptionHandler {

	public APIExcpetionHandler(ErrorAttributes errorAttributes, WebProperties prop,
			ApplicationContext applicationContext,ServerCodecConfigurer configurer) {
		super(errorAttributes, prop.getResources(), applicationContext);
		// TODO Auto-generated constructor stub
		this.setMessageReaders(configurer.getReaders());
		this.setMessageWriters(configurer.getWriters());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		// TODO Auto-generated method stub
		return RouterFunctions.route(RequestPredicates.all(), this::getErrors);
	}
	
	private Mono<ServerResponse> getErrors(ServerRequest request){
		Map<String,Object> map = this.getErrorAttributes(request, ErrorAttributeOptions.defaults());
		return ServerResponse.status(404).body(BodyInserters.fromValue(map));
	}

}
@Component
class ErrorAttirbutes extends DefaultErrorAttributes{

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = new HashMap<>();
        Throwable error = getError(request);
        errorMap.put("message", error.getMessage());
        errorMap.put("time", new Date(System.currentTimeMillis()));
        errorMap.put("endpoint url ", request.path());
        return errorMap;
    }
	
}
