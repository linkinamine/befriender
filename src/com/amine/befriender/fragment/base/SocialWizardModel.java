/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amine.befriender.fragment.base;

import android.content.Context;

import com.amine.befriender.fragment.dialog.AlertDialogFragment;
import com.amine.befriender.wizard.model.AbstractWizardModel;
import com.amine.befriender.wizard.model.PageList;
import com.amine.befriender.wizard.model.SingleFixedChoicePage;

public class SocialWizardModel extends AbstractWizardModel  {
	public SocialWizardModel(Context context) {
		super(context);
	}

	@Override
	protected PageList onNewRootPageList() {

		return new PageList(new SingleFixedChoicePage(this, "Add via Facebok ")
				.setChoices("yes", "no").setRequired(true),

		new SingleFixedChoicePage(this, "Follow on Twitter ").setChoices("yes",
				"no").setRequired(true),

		new SingleFixedChoicePage(this, "Add via Linkedin ").setChoices("yes",
				"no").setRequired(true),

		new SingleFixedChoicePage(this, "Add To Google + ").setChoices("yes",
				"no").setRequired(true)

		);

		// new CustomerInfoPage(this, "Your info").setRequired(true)

	}

	


}
