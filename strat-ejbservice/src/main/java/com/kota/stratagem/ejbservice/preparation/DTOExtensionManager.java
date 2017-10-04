package com.kota.stratagem.ejbservice.preparation;

import java.util.List;

public interface DTOExtensionManager {

	Object prepare(Object representor);

	Object prepareForOwner(Object representor);

	List<?> prepareBatch(List<?> representors);

}
