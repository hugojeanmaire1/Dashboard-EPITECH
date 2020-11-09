package App.Routes;

import org.springframework.web.bind.annotation.GetMapping;

public interface IController<T> {

    @GetMapping(path="/", produces = "application/json")
    public T get();

}
