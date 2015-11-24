package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.domain.interactor.messages.CreateReplyUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.models.CreateReplyModel;
import com.itacit.healthcare.presentation.messages.views.NewReplyView;

import rx.Subscriber;

/**
 * Created by root on 19.11.15.
 */
public class NewReplyPresenter extends BasePresenter<NewReplyView> {

	private CreateReplyUseCase createReplyUseCase;

	public NewReplyPresenter (CreateReplyUseCase createReplyUseCase) {
		this.createReplyUseCase = createReplyUseCase;
	}

	@Override
	protected void onAttachedView(@NonNull NewReplyView view) {

	}

	public void sendReply(String messageId, Boolean isPrivate, String body) {
		CreateReplyModel replyModel = new CreateReplyModel();
		replyModel.setId(messageId);
		replyModel.setIsPrivate(isPrivate);
		replyModel.setBody(body);

		createReplyUseCase.execute(new Subscriber<Reply>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Reply reply) {
				Log.v("My Log", "Reply sended with id: " + replyModel.getId());
			}
		}, replyModel);
	}
}

