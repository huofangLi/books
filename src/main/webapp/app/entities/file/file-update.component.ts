import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IFile, File } from 'app/shared/model/file.model';
import { FileService } from './file.service';

@Component({
  selector: 'jhi-file-update',
  templateUrl: './file-update.component.html'
})
export class FileUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    userId: [],
    fileName: [],
    fileContent: [],
    createTime: []
  });

  constructor(protected fileService: FileService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ file }) => {
      this.updateForm(file);
    });
  }

  updateForm(file: IFile) {
    this.editForm.patchValue({
      id: file.id,
      userId: file.userId,
      fileName: file.fileName,
      fileContent: file.fileContent,
      createTime: file.createTime != null ? file.createTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const file = this.createFromForm();
    if (file.id !== undefined) {
      this.subscribeToSaveResponse(this.fileService.update(file));
    } else {
      this.subscribeToSaveResponse(this.fileService.create(file));
    }
  }

  private createFromForm(): IFile {
    return {
      ...new File(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      fileName: this.editForm.get(['fileName']).value,
      fileContent: this.editForm.get(['fileContent']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFile>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
